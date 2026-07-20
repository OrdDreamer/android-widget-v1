package com.photowidget.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.decode.BitmapFactoryDecoder
import coil.decode.ExifOrientationPolicy
import coil.request.ImageRequest
import com.photowidget.data.FrameStyle
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
    frameStyle: FrameStyle = FrameStyle.POLAROID,
    contentScale: ContentScale? = null,
    alignment: Alignment = imageAlignment.toComposeAlignment(),
    retryKey: Int = 0,
    errorContent: (@Composable () -> Unit)? = null,
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
    val request = remember(imageUri, retryKey) {
        ImageRequest.Builder(context)
            .data(imageUri)
            .decoderFactory(
                BitmapFactoryDecoder.Factory(
                    exifOrientationPolicy = ExifOrientationPolicy.IGNORE,
                ),
            )
            .crossfade(false)
            .build()
    }
    val colorFilter = remember(frameStyle) {
        if (frameStyle == FrameStyle.VINTAGE) {
            ColorFilter.colorMatrix(
                ColorMatrix(
                    floatArrayOf(
                        0.9f, 0.2f, 0.05f, 0f, 10f,
                        0.15f, 0.8f, 0.1f, 0f, 5f,
                        0.1f, 0.15f, 0.6f, 0f, 0f,
                        0f, 0f, 0f, 1f, 0f,
                    ),
                ),
            )
        } else {
            null
        }
    }

    SubcomposeAsyncImage(
        model = request,
        contentDescription = null,
        modifier = modifier.graphicsLayer {
            rotationZ = totalRotation.toFloat()
        },
        contentScale = resolvedContentScale,
        alignment = alignment,
        colorFilter = colorFilter,
    ) {
        val state = painter.state
        when (state) {
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            is AsyncImagePainter.State.Error -> {
                if (errorContent != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationZ = -totalRotation.toFloat() },
                        contentAlignment = Alignment.Center,
                    ) {
                        errorContent()
                    }
                }
            }
            else -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}
