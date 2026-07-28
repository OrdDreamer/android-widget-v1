package com.photowidget.ui.components.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.RotateLeft
import androidx.compose.material.icons.automirrored.rounded.RotateRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * Design-system RotationControl (controls/RotationControl.jsx): a full 45deg-snap dial. Built for
 * component-library completeness but currently **unused** — "Widget Settings.dc.html" drives
 * rotation with a simple IconButton pair (90deg steps, matching [com.photowidget.data.WidgetConfig]
 * and the untouched renderer's 90deg-only rotation model). Wire this in only if the data
 * model/renderer ever grow free/45deg rotation support.
 */
@Composable
fun PhotoWidgetRotationControl(
    degrees: Int,
    onDegreesChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        StepButton(icon = Icons.AutoMirrored.Rounded.RotateLeft, onClick = { onDegreesChange(snap(degrees - 45)) })

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
            )
            for (tick in 0 until 8) {
                val tickDegrees = tick * 45
                val active = snap(degrees) == tickDegrees
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .rotate(tickDegrees.toFloat())
                        .background(if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline, CircleShape),
                )
            }
            Box(
                modifier = Modifier
                    .size(width = 4.dp, height = 22.dp)
                    .rotate(degrees.toFloat())
                    .background(MaterialTheme.colorScheme.primary),
            )
        }

        Text(
            text = "${snap(degrees)}°",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(width = 48.dp, height = 24.dp),
        )

        StepButton(icon = Icons.AutoMirrored.Rounded.RotateRight, onClick = { onDegreesChange(snap(degrees + 45)) })
    }
}

@Composable
private fun StepButton(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface, modifier = Modifier.size(16.dp))
    }
}

private fun snap(degrees: Int): Int {
    val normalized = ((degrees % 360) + 360) % 360
    return (normalized / 45.0).roundToInt() * 45 % 360
}
