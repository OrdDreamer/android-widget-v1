package com.photowidget.widget

import android.appwidget.AppWidgetManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.photowidget.MainActivity
import com.photowidget.PhotoWidgetApp
import com.photowidget.R
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetClickAction
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import kotlinx.coroutines.runBlocking
import kotlin.math.max
import kotlin.math.min

object PhotoWidgetRenderer {

    private const val TAG = "PhotoWidgetRenderer"

    fun buildViews(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        widgetOptions: android.os.Bundle? = null,
    ): RemoteViews {
        return try {
            buildViewsInternal(context, appWidgetManager, appWidgetId, widgetOptions)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to build widget views for id=$appWidgetId", e)
            placeholderViews(context)
        }
    }

    fun update(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        widgetOptions: android.os.Bundle? = null,
    ) {
        appWidgetManager.updateAppWidget(
            appWidgetId,
            buildViews(context, appWidgetManager, appWidgetId, widgetOptions),
        )
    }

    private fun buildViewsInternal(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        widgetOptions: android.os.Bundle?,
    ): RemoteViews {
        val app = context.applicationContext as PhotoWidgetApp
        val repository = app.widgetConfigRepository

        val config = runBlocking {
            repository.ensureWidgetConfig(appWidgetId)
            repository.getConfig(appWidgetId)
        }

        val options = widgetOptions ?: appWidgetManager.getAppWidgetOptions(appWidgetId)
        val (widthDp, heightDp) = widgetSizeDp(options, context.resources.configuration.orientation)
        val density = context.resources.displayMetrics.density
        val widthPx = (widthDp * density).toInt().coerceAtLeast(1)
        val heightPx = (heightDp * density).toInt().coerceAtLeast(1)
        logWidgetSizing(appWidgetId, options, widthDp, heightDp, widthPx, heightPx)

        val views = RemoteViews(context.packageName, R.layout.widget_photo)

        val imageUri = config.imageUri
        if (imageUri == null) {
            showPlaceholder(views)
            bindClickAction(context, views, appWidgetId, config)
            return views
        }

        WidgetUriHelper.ensureReadPermission(context, imageUri.toUri())
        val bitmap = WidgetImageCache.renderBitmap(
            context = context,
            sourceUri = imageUri.toUri(),
            config = config,
            targetWidthPx = widthPx,
            targetHeightPx = heightPx,
            density = density,
        )

        if (bitmap != null) {
            views.setViewVisibility(R.id.widget_placeholder, View.GONE)
            views.setViewVisibility(R.id.widget_image, View.VISIBLE)
            views.setInt(R.id.widget_root, "setBackgroundColor", android.graphics.Color.TRANSPARENT)
            views.setImageViewBitmap(R.id.widget_image, bitmap)
        } else {
            showPlaceholder(views)
        }
        bindClickAction(context, views, appWidgetId, config)

        return views
    }

    private fun placeholderViews(context: Context): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.widget_photo)
        showPlaceholder(views)
        return views
    }

    private fun showPlaceholder(views: RemoteViews) {
        views.setViewVisibility(R.id.widget_image, View.GONE)
        views.setViewVisibility(R.id.widget_placeholder, View.VISIBLE)
        views.setInt(R.id.widget_root, "setBackgroundColor", 0xFF121212.toInt())
    }

    private fun bindClickAction(
        context: Context,
        views: RemoteViews,
        appWidgetId: Int,
        config: WidgetConfig,
    ) {
        val pendingIntent = when (config.clickAction) {
            WidgetClickAction.DECORATIVE -> createNoopPendingIntent(context, appWidgetId)
            WidgetClickAction.OPEN_APP -> createOpenAppPendingIntent(context, appWidgetId)
            WidgetClickAction.OPEN_WIDGET_SETTINGS -> createOpenWidgetPendingIntent(context, appWidgetId)
        }
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)
        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent)
    }

    private fun createNoopPendingIntent(context: Context, appWidgetId: Int): PendingIntent {
        val intent = Intent(context, PhotoWidgetReceiver::class.java).apply {
            action = "com.photowidget.widget.NOOP"
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }
        return PendingIntent.getBroadcast(
            context,
            appWidgetId * 10 + 1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun createOpenAppPendingIntent(context: Context, appWidgetId: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            action = "com.photowidget.widget.OPEN_APP.$appWidgetId"
        }
        return PendingIntent.getActivity(
            context,
            appWidgetId * 10 + 2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun createOpenWidgetPendingIntent(context: Context, appWidgetId: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            action = "com.photowidget.widget.OPEN_WIDGET.$appWidgetId"
            putExtra(MainActivity.EXTRA_EDIT_WIDGET_ID, appWidgetId)
        }
        return PendingIntent.getActivity(
            context,
            appWidgetId * 10 + 3,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun widgetSizeDp(options: android.os.Bundle, orientation: Int): Pair<Int, Int> {
        val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 110)
        val minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 110)
        val maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, minWidth)
        val maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, minHeight)

        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            maxWidth.coerceAtLeast(1) to minHeight.coerceAtLeast(1)
        } else {
            minWidth.coerceAtLeast(1) to maxHeight.coerceAtLeast(1)
        }
    }

    private fun logWidgetSizing(
        appWidgetId: Int,
        options: android.os.Bundle,
        widthDp: Int,
        heightDp: Int,
        widthPx: Int,
        heightPx: Int,
    ) {
        val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, -1)
        val minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, -1)
        val maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, -1)
        val maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, -1)

        Log.d(
            TAG,
            "widgetId=$appWidgetId options(min=${minWidth}x$minHeight, max=${maxWidth}x$maxHeight) " +
                "chosen=${widthDp}x${heightDp}dp render=${widthPx}x${heightPx}px",
        )
    }
}

object WidgetImageCache {

    private const val MAX_BITMAP_BYTES = 350_000
    private const val MAX_BITMAP_DIMENSION = 480

    fun renderBitmap(
        context: Context,
        sourceUri: Uri,
        config: WidgetConfig,
        targetWidthPx: Int,
        targetHeightPx: Int,
        density: Float,
    ): Bitmap? {
        val source = decodeSampledBitmap(
            context = context,
            uri = sourceUri,
            reqWidth = targetWidthPx,
            reqHeight = targetHeightPx,
        ) ?: return null

        val scaled = renderScaled(source, config.scaleMode, targetWidthPx, targetHeightPx)
        if (scaled !== source) {
            source.recycle()
        }

        val preserveAlpha = config.shape != WidgetShape.RECTANGLE ||
            config.scaleMode == ScaleMode.CONTAIN
        val compressed = compressForRemoteViews(scaled, preserveAlpha)
        if (compressed !== scaled) {
            scaled.recycle()
        }

        val scale = compressed.width.toFloat() / targetWidthPx
        val shaped = applyShape(
            source = compressed,
            config = config,
            targetWidth = compressed.width,
            targetHeight = compressed.height,
            density = density * scale,
        )
        if (shaped !== compressed) {
            compressed.recycle()
        }

        return shaped
    }

    private fun compressForRemoteViews(bitmap: Bitmap, preserveAlpha: Boolean): Bitmap {
        var result = bitmap

        val maxSide = max(result.width, result.height)
        if (maxSide > MAX_BITMAP_DIMENSION) {
            val scale = MAX_BITMAP_DIMENSION.toFloat() / maxSide
            val scaled = Bitmap.createScaledBitmap(
                result,
                (result.width * scale).toInt().coerceAtLeast(1),
                (result.height * scale).toInt().coerceAtLeast(1),
                true,
            )
            if (scaled !== result) {
                result.recycle()
            }
            result = scaled
        }

        if (!preserveAlpha && result.byteCount > MAX_BITMAP_BYTES) {
            val rgb = result.copy(Bitmap.Config.RGB_565, false)
            if (rgb !== result) {
                result.recycle()
            }
            result = rgb
        }

        while (result.byteCount > MAX_BITMAP_BYTES) {
            val scale = 0.85f
            val smaller = Bitmap.createScaledBitmap(
                result,
                (result.width * scale).toInt().coerceAtLeast(1),
                (result.height * scale).toInt().coerceAtLeast(1),
                true,
            )
            if (smaller !== result) {
                result.recycle()
            }
            result = smaller
        }

        return result
    }

    private fun renderScaled(
        source: Bitmap,
        scaleMode: ScaleMode,
        targetWidth: Int,
        targetHeight: Int,
    ): Bitmap {
        val output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val scale = when (scaleMode) {
            ScaleMode.COVER -> maxOf(
                targetWidth.toFloat() / source.width,
                targetHeight.toFloat() / source.height,
            )
            ScaleMode.CONTAIN -> minOf(
                targetWidth.toFloat() / source.width,
                targetHeight.toFloat() / source.height,
            )
        }

        val scaledWidth = source.width * scale
        val scaledHeight = source.height * scale
        val dx = (targetWidth - scaledWidth) / 2f
        val dy = (targetHeight - scaledHeight) / 2f

        val matrix = Matrix().apply {
            postScale(scale, scale)
            postTranslate(dx, dy)
        }
        canvas.drawBitmap(source, matrix, paint)
        return output
    }

    private fun applyShape(
        source: Bitmap,
        config: WidgetConfig,
        targetWidth: Int,
        targetHeight: Int,
        density: Float,
    ): Bitmap {
        return when (config.shape) {
            WidgetShape.RECTANGLE -> source
            WidgetShape.ROUNDED_RECT -> roundCorners(source, config.cornerRadiusDp * density)
            WidgetShape.CIRCLE -> roundCorners(source, min(targetWidth, targetHeight) / 2f)
        }
    }

    private fun roundCorners(source: Bitmap, radiusPx: Float): Bitmap {
        val width = source.width
        val height = source.height
        val radius = radiusPx.coerceIn(0f, min(width, height) / 2f)

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val clipPath = Path().apply {
            addRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                radius,
                radius,
                Path.Direction.CW,
            )
        }
        canvas.clipPath(clipPath)
        canvas.drawBitmap(source, 0f, 0f, null)
        return output
    }

    private fun decodeSampledBitmap(
        context: Context,
        uri: Uri,
        reqWidth: Int,
        reqHeight: Int,
    ): Bitmap? {
        return try {
            val resolver = context.contentResolver
            val boundsOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            resolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, boundsOptions)
            }
            if (boundsOptions.outWidth <= 0 || boundsOptions.outHeight <= 0) {
                return null
            }
            val sampleSize = calculateInSampleSize(boundsOptions, reqWidth * 2, reqHeight * 2)
            val decodeOptions = BitmapFactory.Options().apply {
                inSampleSize = sampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            resolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, decodeOptions)
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int,
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            var halfHeight = height / 2
            var halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}

object WidgetUriHelper {

    fun ensureReadPermission(context: Context, uri: Uri) {
        try {
            context.contentResolver.takePersistableUriPermission(
                uri,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        } catch (_: SecurityException) {
        }
    }
}
