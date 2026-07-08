package com.photowidget.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.net.Uri
import android.util.Log
import com.photowidget.data.ImageAlignment
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

object WidgetImageCache {

    private const val TAG = "WidgetImageCache"

    // ~2.1 MB fits 900×1200 RGB_565 on tested Pixel/Android 17; ARGB_565 uses 4 bytes/px.
    private const val MAX_BITMAP_BYTES = 2_100_000

    fun renderForWidget(
        context: Context,
        sourceUri: Uri,
        config: WidgetConfig,
        targetWidthPx: Int,
        targetHeightPx: Int,
        density: Float,
    ): Bitmap? {
        val (renderWidth, renderHeight) = computeRenderDimensions(targetWidthPx, targetHeightPx, config)
        val bitmap = renderBitmap(
            context = context,
            sourceUri = sourceUri,
            config = config,
            renderWidthPx = renderWidth,
            renderHeightPx = renderHeight,
            density = density,
            displayWidthPx = targetWidthPx,
            displayHeightPx = targetHeightPx,
        ) ?: return null

        val output = finalizeForRemoteViews(bitmap, config)
        if (output !== bitmap) {
            bitmap.recycle()
        }
        Log.d(
            TAG,
            "render target=${targetWidthPx}x${targetHeightPx}px " +
                "bitmap=${output.width}x${output.height}px bytes=${output.byteCount} " +
                "alpha=${needsAlpha(config)}",
        )
        return output
    }

    private fun needsAlpha(config: WidgetConfig): Boolean {
        return config.shape != WidgetShape.RECTANGLE || config.scaleMode == ScaleMode.CONTAIN
    }

    private fun computeRenderDimensions(
        targetWidthPx: Int,
        targetHeightPx: Int,
        config: WidgetConfig,
    ): Pair<Int, Int> {
        val bytesPerPixel = if (needsAlpha(config)) 4 else 2
        val maxPixels = MAX_BITMAP_BYTES / bytesPerPixel
        val requestedPixels = targetWidthPx.toLong() * targetHeightPx
        if (requestedPixels <= maxPixels) {
            return targetWidthPx to targetHeightPx
        }
        val scale = sqrt(maxPixels.toFloat() / requestedPixels)
        return (
            (targetWidthPx * scale).toInt().coerceAtLeast(1) to
                (targetHeightPx * scale).toInt().coerceAtLeast(1)
            )
    }

    private fun renderBitmap(
        context: Context,
        sourceUri: Uri,
        config: WidgetConfig,
        renderWidthPx: Int,
        renderHeightPx: Int,
        density: Float,
        displayWidthPx: Int,
        displayHeightPx: Int,
    ): Bitmap? {
        val exifRotation = ImageOrientationHelper.readExifRotationDegrees(context, sourceUri)
        val totalRotation = ImageOrientationHelper.totalRotation(exifRotation, config.rotationDegrees)
        val (decodeWidth, decodeHeight) = if (totalRotation % 180 != 0) {
            renderHeightPx to renderWidthPx
        } else {
            renderWidthPx to renderHeightPx
        }

        val source = decodeSampledBitmap(
            context = context,
            uri = sourceUri,
            reqWidth = decodeWidth,
            reqHeight = decodeHeight,
            exifRotation = exifRotation,
        ) ?: return null

        val transformed = renderTransformed(
            source = source,
            totalRotation = totalRotation,
            scaleMode = config.scaleMode,
            alignment = config.imageAlignment,
            targetWidth = renderWidthPx,
            targetHeight = renderHeightPx,
            transparentBackground = needsAlpha(config),
        )
        if (transformed !== source) {
            source.recycle()
        }

        return applyShape(
            source = transformed,
            config = config,
            density = density * (renderWidthPx.toFloat() / displayWidthPx.coerceAtLeast(1)),
        )
    }

    private fun renderTransformed(
        source: Bitmap,
        totalRotation: Int,
        scaleMode: ScaleMode,
        alignment: ImageAlignment,
        targetWidth: Int,
        targetHeight: Int,
        transparentBackground: Boolean,
    ): Bitmap {
        val output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        if (!transparentBackground) {
            canvas.drawColor(android.graphics.Color.BLACK)
        }

        val orientedWidth = if (totalRotation % 180 != 0) source.height else source.width
        val orientedHeight = if (totalRotation % 180 != 0) source.width else source.height
        val scale = when (scaleMode) {
            ScaleMode.COVER -> maxOf(
                targetWidth.toFloat() / orientedWidth,
                targetHeight.toFloat() / orientedHeight,
            )
            ScaleMode.CONTAIN -> minOf(
                targetWidth.toFloat() / orientedWidth,
                targetHeight.toFloat() / orientedHeight,
            )
        }

        val scaledWidth = orientedWidth * scale
        val scaledHeight = orientedHeight * scale
        val dx = alignment.offsetX(targetWidth.toFloat(), scaledWidth)
        val dy = alignment.offsetY(targetHeight.toFloat(), scaledHeight)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val matrix = Matrix().apply {
            postTranslate(-source.width / 2f, -source.height / 2f)
            postRotate(totalRotation.toFloat())
            postScale(scale, scale)
            postTranslate(dx + scaledWidth / 2f, dy + scaledHeight / 2f)
        }
        canvas.drawBitmap(source, matrix, paint)
        return output
    }

    private fun finalizeForRemoteViews(bitmap: Bitmap, config: WidgetConfig): Bitmap {
        var result = bitmap
        if (!needsAlpha(config) && result.config != Bitmap.Config.RGB_565) {
            val rgb = result.copy(Bitmap.Config.RGB_565, false)
            if (rgb !== result) {
                result.recycle()
            }
            result = rgb
        }

        if (result.byteCount <= MAX_BITMAP_BYTES) {
            return result
        }

        val scale = sqrt(MAX_BITMAP_BYTES.toFloat() / result.byteCount) * 0.98f
        val smaller = scaleBitmap(
            source = result,
            newWidth = (result.width * scale).toInt().coerceAtLeast(1),
            newHeight = (result.height * scale).toInt().coerceAtLeast(1),
            preserveAlpha = needsAlpha(config),
        )
        if (smaller !== result) {
            result.recycle()
        }
        return smaller
    }

    private fun scaleBitmap(
        source: Bitmap,
        newWidth: Int,
        newHeight: Int,
        preserveAlpha: Boolean,
    ): Bitmap {
        if (source.width == newWidth && source.height == newHeight) {
            return source
        }
        val config = if (preserveAlpha) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val output = Bitmap.createBitmap(newWidth, newHeight, config)
        val canvas = Canvas(output)
        if (!preserveAlpha) {
            canvas.drawColor(android.graphics.Color.BLACK)
        }
        val paint = Paint(Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
        val matrix = Matrix().apply {
            setScale(
                newWidth.toFloat() / source.width,
                newHeight.toFloat() / source.height,
            )
        }
        canvas.drawBitmap(source, matrix, paint)
        return output
    }

    private fun applyShape(
        source: Bitmap,
        config: WidgetConfig,
        density: Float,
    ): Bitmap {
        return when (config.shape) {
            WidgetShape.RECTANGLE -> source
            WidgetShape.ROUNDED_RECT -> roundCorners(source, config.cornerRadiusDp * density)
            WidgetShape.CIRCLE -> roundCorners(source, min(source.width, source.height) / 2f)
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
        exifRotation: Int,
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

            val (orientedWidth, orientedHeight) = ImageOrientationHelper.orientedBounds(
                boundsOptions.outWidth,
                boundsOptions.outHeight,
                exifRotation,
            )
            val sampleSize = calculateInSampleSize(
                width = orientedWidth,
                height = orientedHeight,
                reqWidth = reqWidth,
                reqHeight = reqHeight,
            )
            val decodeOptions = BitmapFactory.Options().apply {
                inSampleSize = sampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
                inScaled = false
            }
            resolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, decodeOptions)
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun calculateInSampleSize(
        width: Int,
        height: Int,
        reqWidth: Int,
        reqHeight: Int,
    ): Int {
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
