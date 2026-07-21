package com.photowidget.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Dashed outline matching the mock's `border: ... dashed ...` empty-state placeholders. */
fun Modifier.dashedBorder(
    width: Dp,
    color: Color,
    shape: Shape,
    on: Dp = 6.dp,
    off: Dp = 5.dp,
): Modifier = drawWithContent {
    drawContent()
    val stroke = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(on.toPx(), off.toPx()), 0f),
    )
    when (val outline = shape.createOutline(size, layoutDirection, this)) {
        is Outline.Rounded -> {
            val radius = outline.roundRect.topLeftCornerRadius
            drawRoundRect(
                color = color,
                topLeft = Offset(outline.roundRect.left, outline.roundRect.top),
                size = Size(outline.roundRect.width, outline.roundRect.height),
                cornerRadius = CornerRadius(radius.x, radius.y),
                style = stroke,
            )
        }
        is Outline.Rectangle -> drawRect(
            color = color,
            topLeft = outline.rect.topLeft,
            size = outline.rect.size,
            style = stroke,
        )
        is Outline.Generic -> drawPath(path = outline.path, color = color, style = stroke)
    }
}
