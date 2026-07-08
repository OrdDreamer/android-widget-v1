package com.photowidget.widget

import android.content.Context
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

object ImageOrientationHelper {

    fun readExifRotationDegrees(context: Context, uri: Uri): Int {
        return try {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                rotationFromExif(ExifInterface(stream))
            } ?: 0
        } catch (_: Exception) {
            0
        }
    }

    fun totalRotation(exifRotationDegrees: Int, userRotationDegrees: Int): Int {
        return normalizeRotation(exifRotationDegrees + userRotationDegrees)
    }

    fun normalizeRotation(degrees: Int): Int {
        return ((degrees % 360) + 360) % 360
    }

    fun orientedBounds(width: Int, height: Int, exifRotationDegrees: Int): Pair<Int, Int> {
        return if (normalizeRotation(exifRotationDegrees) % 180 != 0) {
            height to width
        } else {
            width to height
        }
    }

    private fun rotationFromExif(exif: ExifInterface): Int {
        return when (
            exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL,
            )
        ) {
            ExifInterface.ORIENTATION_ROTATE_90,
            ExifInterface.ORIENTATION_TRANSPOSE,
            -> 90

            ExifInterface.ORIENTATION_ROTATE_180 -> 180

            ExifInterface.ORIENTATION_ROTATE_270,
            ExifInterface.ORIENTATION_TRANSVERSE,
            -> 270

            else -> 0
        }
    }
}
