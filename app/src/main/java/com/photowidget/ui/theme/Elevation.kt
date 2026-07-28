package com.photowidget.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// The brand explicitly avoids M3's tonal-elevation color shift — real, warm-tinted drop shadows
// carry depth instead (guidelines/jetpack-compose.md § Elevation). Alpha per tier approximates
// the web tokens' `--shadow-sm/md/lg` (rgba(33,31,27, .07/.08/.12)); dark theme uses a true-black
// tint since a warm-ink shadow would be invisible against an already-dark background.
object PhotoWidgetElevation {
    val sm: Dp = 2.dp
    val md: Dp = 8.dp
    val lg: Dp = 20.dp
}

private fun alphaFor(elevation: Dp): Float = when (elevation) {
    PhotoWidgetElevation.sm -> 0.07f
    PhotoWidgetElevation.md -> 0.08f
    else -> 0.12f
}

/** Warm-tinted drop shadow, replacing M3's default tonal elevation per the brand's flat-surface rule. */
fun Modifier.photoWidgetShadow(
    elevation: Dp,
    shape: Shape = RoundedCornerShape(RadiusLg),
): Modifier = composed {
    val tint = shadowTint().copy(alpha = alphaFor(elevation))
    shadow(elevation = elevation, shape = shape, ambientColor = tint, spotColor = tint)
}
