package com.photowidget.ui.components.media

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke as DrawStroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.data.FrameStyle
import com.photowidget.data.ImageAlignment
import com.photowidget.data.ScaleMode
import com.photowidget.ui.WidgetImagePreview
import com.photowidget.ui.theme.RadiusLg
import com.photowidget.ui.theme.StatusDanger
import com.photowidget.ui.theme.StatusDangerSubtleBg
import com.photowidget.ui.theme.StatusDangerSubtleBorder

/**
 * Design-system PhotoPreview (media/PhotoPreview.jsx): empty/loaded/error states. Wraps
 * [WidgetImagePreview] (Coil + EXIF rotation + vintage color-matrix, shared with the widget
 * rendering pipeline) rather than reimplementing image loading.
 */
@Composable
fun PhotoWidgetPhotoPreview(
    imageUri: String?,
    rotationDegrees: Int,
    imageAlignment: ImageAlignment,
    scaleMode: ScaleMode,
    frameStyle: FrameStyle,
    onSelectPhoto: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 240.dp,
) {
    val shape = RoundedCornerShape(RadiusLg)
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        if (imageUri == null) {
            EmptyContent(onClick = onSelectPhoto)
        } else {
            WidgetImagePreview(
                imageUri = imageUri,
                rotationDegrees = rotationDegrees,
                imageAlignment = imageAlignment,
                scaleMode = scaleMode,
                frameStyle = frameStyle,
                modifier = Modifier.size(size),
                errorContent = { ErrorContent() },
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(36.dp)
                    .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                    .clickable(onClick = onSelectPhoto),
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun BoxScope.EmptyContent(onClick: () -> Unit) {
    val borderColor = MaterialTheme.colorScheme.outline
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .clickable(onClick = onClick)
            .drawBehind { drawDashedRoundRect(borderColor) }
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Rounded.AddPhotoAlternate,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = stringResource(R.string.no_photo_selected),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Composable
private fun BoxScope.ErrorContent() {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .background(StatusDangerSubtleBg, RoundedCornerShape(RadiusLg))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Rounded.BrokenImage,
            contentDescription = null,
            tint = StatusDanger,
            modifier = Modifier.size(32.dp),
        )
        Text(
            text = stringResource(R.string.photo_load_error),
            style = MaterialTheme.typography.bodySmall,
            color = StatusDanger,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawDashedRoundRect(color: Color) {
    drawRoundRect(
        color = color,
        cornerRadius = CornerRadius(x = 14.dp.toPx(), y = 14.dp.toPx()),
        style = DrawStroke(
            width = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 6.dp.toPx()), 0f),
        ),
    )
}
