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
import com.photowidget.data.ImageAlignment
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import kotlin.math.max
import kotlin.math.min

object WidgetImageCache {

    // RemoteViews parcel limit is ~1 MB; keep bitmap payload safely below it.
    private const val MAX_BITMAP_BYTES = 950_000
    private const val DECODE_QUALITY_FACTOR = 1.35f

    fun renderForWidget(
        context: Context,
        sourceUri: Uri,
        config: WidgetConfig,
        targetWidthPx: Int,
        targetHeightPx: Int,
        density: Float,
    ): Bitmap? {
        val bitmap = renderBitmap(
            context = context,
            sourceUri = sourceUri,
            config = config,
            targetWidthPx = targetWidthPx,
            targetHeightPx = targetHeightPx,
            density = density,
        ) ?: return null

        val fitted = fitForRemoteViews(bitmap, config)
        if (fitted !== bitmap) {
            bitmap.recycle()
        }
        return fitted
    }

    private fun renderBitmap(
        context: Context,
        sourceUri: Uri,
        config: WidgetConfig,
        targetWidthPx: Int,
        targetHeightPx: Int,
        density: Float,
    ): Bitmap? {
        val exifRotation = ImageOrientationHelper.readExifRotationDegrees(context, sourceUri)
        val totalRotation = ImageOrientationHelper.totalRotation(exifRotation, config.rotationDegrees)
        val (decodeWidth, decodeHeight) = if (totalRotation % 180 != 0) {
            targetHeightPx to targetWidthPx
        } else {
            targetWidthPx to targetHeightPx
        }

        val source = decodeSampledBitmap(
            context = context,
            uri = sourceUri,
            reqWidth = (decodeWidth * DECODE_QUALITY_FACTOR).toInt().coerceAtLeast(decodeWidth),
            reqHeight = (decodeHeight * DECODE_QUALITY_FACTOR).toInt().coerceAtLeast(decodeHeight),
            exifRotation = exifRotation,
        ) ?: return null

        val transformed = renderTransformed(
            source = source,
            totalRotation = totalRotation,
            scaleMode = config.scaleMode,
            alignment = config.imageAlignment,
            targetWidth = targetWidthPx,
            targetHeight = targetHeightPx,
        )
        if (transformed !== source) {
            source.recycle()
        }

        return applyShape(
            source = transformed,
            config = config,
            targetWidth = transformed.width,
            targetHeight = transformed.height,
            density = density,
        )
    }

    private fun renderTransformed(
        source: Bitmap,
        totalRotation: Int,
        scaleMode: ScaleMode,
        alignment: ImageAlignment,
        targetWidth: Int,
        targetHeight: Int,
    ): Bitmap {
        val output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

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

        val matrix = Matrix().apply {
            postTranslate(-source.width / 2f, -source.height / 2f)
            postRotate(totalRotation.toFloat())
            postScale(scale, scale)
            postTranslate(dx + scaledWidth / 2f, dy + scaledHeight / 2f)
        }
        canvas.drawBitmap(source, matrix, paint)
        return output
    }

    private fun fitForRemoteViews(bitmap: Bitmap, config: WidgetConfig): Bitmap {
        var result = bitmap
        val preserveAlpha = config.shape != WidgetShape.RECTANGLE ||
            config.scaleMode == ScaleMode.CONTAIN

        if (!preserveAlpha && result.byteCount > MAX_BITMAP_BYTES) {
            val rgb = result.copy(Bitmap.Config.RGB_565, false)
            if (rgb !== result) {
                result.recycle()
            }
            result = rgb
        }

        while (result.byteCount > MAX_BITMAP_BYTES) {
            val scale = 0.94f
            val smaller = scaleBitmapHighQuality(
                result,
                (result.width * scale).toInt().coerceAtLeast(1),
                (result.height * scale).toInt().coerceAtLeast(1),
            )
            if (smaller !== result) {
                result.recycle()
            }
            result = smaller
        }

        return result
    }

    private fun scaleBitmapHighQuality(source: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        if (source.width == newWidth && source.height == newHeight) {
            return source
        }
        val output = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
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
