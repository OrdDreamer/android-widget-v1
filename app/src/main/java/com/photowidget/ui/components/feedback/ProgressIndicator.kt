package com.photowidget.ui.components.feedback

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Design-system ProgressIndicator (feedback/ProgressIndicator.jsx): 6dp pill track, determinate
 * fill or a 40%-wide sweeping bar for indeterminate (not M3's default indeterminate pattern).
 */
@Composable
fun PhotoWidgetProgressIndicator(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    indeterminate: Boolean = false,
) {
    val shape = RoundedCornerShape(percent = 50)
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(6.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape),
    ) {
        if (indeterminate) {
            val transition = rememberInfiniteTransition(label = "progressSweep")
            val fraction by transition.animateFloat(
                initialValue = -0.4f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Restart),
                label = "progressSweepFraction",
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(6.dp)
                    .offset(x = maxWidth * fraction)
                    .background(MaterialTheme.colorScheme.primary, shape),
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value.coerceIn(0f, 1f))
                    .height(6.dp)
                    .background(MaterialTheme.colorScheme.primary, shape),
            )
        }
    }
}
