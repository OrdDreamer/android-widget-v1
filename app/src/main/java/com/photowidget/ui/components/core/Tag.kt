package com.photowidget.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.Terracotta700

/** Design-system Tag (core/Tag.jsx): selectable pill, distinct from Chip (used for filter-style toggles). */
@Composable
fun PhotoWidgetTag(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(percent = 50)
    val background = if (selected) MaterialTheme.colorScheme.primaryContainer else androidx.compose.ui.graphics.Color.Transparent
    val border = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    val content = if (selected) Terracotta700 else MaterialTheme.colorScheme.onSurfaceVariant

    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = content,
        modifier = modifier
            .clickable(onClick = onClick)
            .background(background, shape)
            .border(1.dp, border, shape)
            .padding(horizontal = 16.dp, vertical = 7.dp),
    )
}
