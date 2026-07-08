package com.photowidget.widget

import android.appwidget.AppWidgetManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.photowidget.MainActivity
import com.photowidget.PhotoWidgetApp
import com.photowidget.R
import com.photowidget.data.WidgetClickAction
import com.photowidget.data.WidgetConfig
import kotlinx.coroutines.runBlocking

object PhotoWidgetRenderer {

    private const val TAG = "PhotoWidgetRenderer"

    // See AGENTS.md → CRITICAL: «не вдається завантажити віджет».
    // Widget images MUST be delivered via setImageViewBitmap, not setImageViewUri.

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
            showPlaceholder(context, views, config)
            bindClickAction(context, views, appWidgetId, config)
            return views
        }

        WidgetUriHelper.ensureReadPermission(context, imageUri.toUri())
        val bitmap = WidgetImageCache.renderForWidget(
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
            showPlaceholder(context, views, config)
        }
        bindClickAction(context, views, appWidgetId, config)

        return views
    }

    private fun placeholderViews(context: Context): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.widget_photo)
        showPlaceholder(context, views, WidgetConfig())
        return views
    }

    private fun showPlaceholder(context: Context, views: RemoteViews, config: WidgetConfig) {
        views.setViewVisibility(R.id.widget_image, View.GONE)
        views.setViewVisibility(R.id.widget_placeholder, View.VISIBLE)
        views.setInt(R.id.widget_root, "setBackgroundColor", 0xFF121212.toInt())
        val placeholderText = if (config.widgetNumber > 0) {
            val widgetTitle = context.getString(R.string.widget_number, config.widgetNumber)
            context.getString(
                R.string.widget_placeholder_with_number,
                widgetTitle,
                context.getString(R.string.no_photo_selected),
            )
        } else {
            context.getString(R.string.no_photo_selected)
        }
        views.setTextViewText(R.id.widget_placeholder, placeholderText)
    }

    private fun bindClickAction(
        context: Context,
        views: RemoteViews,
        appWidgetId: Int,
        config: WidgetConfig,
    ) {
        val effectiveAction = if (config.imageUri == null) {
            WidgetClickAction.OPEN_WIDGET_SETTINGS
        } else {
            config.clickAction
        }
        val pendingIntent = when (effectiveAction) {
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
