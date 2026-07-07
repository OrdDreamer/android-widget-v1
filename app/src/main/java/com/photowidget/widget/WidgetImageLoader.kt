package com.photowidget.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.net.Uri
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import kotlin.math.min

object WidgetImageLoader {

    private const val MAX_BITMAP_SIZE = 1024

    fun loadBitmap(
        context: Context,
        config: WidgetConfig,
        targetWidthPx: Int,
        targetHeightPx: Int,
        density: Float,
    ): Bitmap? {
        val uriString = config.imageUri ?: return null
        val uri = Uri.parse(uriString)

        val source = decodeSampledBitmap(context, uri, MAX_BITMAP_SIZE) ?: return null
        val width = targetWidthPx.coerceAtLeast(1)
        val height = targetHeightPx.coerceAtLeast(1)

        val scaled = scaleBitmap(source, config.scaleMode, width, height)
        if (scaled !== source) {
            source.recycle()
        }

        val shaped = applyShape(scaled, config, width, height, density)
        if (shaped !== scaled) {
            scaled.recycle()
        }
        return shaped
    }

    private fun decodeSampledBitmap(context: Context, uri: Uri, maxSize: Int): Bitmap? {
        return try {
            val resolver = context.contentResolver
            val boundsOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            resolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, boundsOptions)
            }

            if (boundsOptions.outWidth <= 0 || boundsOptions.outHeight <= 0) {
                return null
            }

            val sampleSize = calculateInSampleSize(boundsOptions, maxSize, maxSize)
            val decodeOptions = BitmapFactory.Options().apply {
                inSampleSize = sampleSize
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }

            resolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream, null, decodeOptions)
            }
        } catch (_: SecurityException) {
            null
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

    private fun scaleBitmap(
        source: Bitmap,
        scaleMode: ScaleMode,
        targetWidth: Int,
        targetHeight: Int,
    ): Bitmap {
        val output = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

        val scale = when (scaleMode) {
            ScaleMode.COVER -> {
                maxOf(
                    targetWidth.toFloat() / source.width,
                    targetHeight.toFloat() / source.height,
                )
            }
            ScaleMode.CONTAIN -> {
                minOf(
                    targetWidth.toFloat() / source.width,
                    targetHeight.toFloat() / source.height,
                )
            }
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
            WidgetShape.ROUNDED_RECT -> {
                val radiusPx = config.cornerRadiusDp * density
                roundCorners(source, radiusPx)
            }
            WidgetShape.CIRCLE -> {
                val radius = min(targetWidth, targetHeight) / 2f
                clipToRoundedRect(source, radius)
            }
        }
    }

    private fun roundCorners(source: Bitmap, radiusPx: Float): Bitmap {
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader

        val rect = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rect, radiusPx, radiusPx, paint)
        return output
    }

    private fun clipToRoundedRect(source: Bitmap, radius: Float): Bitmap {
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader

        val path = Path()
        val rect = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        canvas.drawPath(path, paint)
        return output
    }
}
