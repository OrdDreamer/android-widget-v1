package com.photowidget.ui.components.controls

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.photowidget.ui.theme.StatusDanger
import com.photowidget.ui.theme.statusWarning

/** Design-system CharacterCounter (controls/CharacterCounter.jsx): "{current}/{max}", color-coded near the limit. */
@Composable
fun PhotoWidgetCharacterCounter(current: Int, max: Int, modifier: Modifier = Modifier) {
    val color = when {
        current > max -> StatusDanger
        current >= (max * 0.9f) -> statusWarning()
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    Text(text = "$current/$max", style = MaterialTheme.typography.labelSmall, color = color, modifier = modifier)
}
