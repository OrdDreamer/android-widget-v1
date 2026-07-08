package com.photowidget.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.decode.BitmapFactoryDecoder
import coil.decode.ExifOrientationPolicy
import coil.request.ImageRequest
import com.photowidget.data.ImageAlignment
import com.photowidget.data.ScaleMode
import com.photowidget.widget.ImageOrientationHelper

@Composable
fun WidgetImagePreview(
    imageUri: String,
    rotationDegrees: Int,
    imageAlignment: ImageAlignment,
    scaleMode: ScaleMode,
    modifier: Modifier = Modifier,
    contentScale: ContentScale? = null,
    alignment: Alignment = imageAlignment.toComposeAlignment(),
) {
    val context = LocalContext.current
    val exifRotation = remember(imageUri) {
        ImageOrientationHelper.readExifRotationDegrees(context, imageUri.toUri())
    }
    val totalRotation = remember(exifRotation, rotationDegrees) {
        ImageOrientationHelper.totalRotation(exifRotation, rotationDegrees)
    }
    val resolvedContentScale = contentScale ?: when (scaleMode) {
        ScaleMode.COVER -> ContentScale.Crop
        ScaleMode.CONTAIN -> ContentScale.Fit
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUri)
            .decoderFactory(
                BitmapFactoryDecoder.Factory(
                    exifOrientationPolicy = ExifOrientationPolicy.IGNORE,
                ),
            )
            .crossfade(false)
            .build(),
        contentDescription = null,
        modifier = modifier.graphicsLayer {
            rotationZ = totalRotation.toFloat()
        },
        contentScale = resolvedContentScale,
        alignment = alignment,
    )
}
