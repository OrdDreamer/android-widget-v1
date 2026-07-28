package com.photowidget.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.photowidget.ui.components.forms.SelectOption

/**
 * Design-system Tabs (navigation/Tabs.jsx): thin accent underline indicator, not M3's default pill.
 * Not wired into any of the 7 rebuilt screens (none of them use tabs) — built for component-library
 * completeness.
 */
@Composable
fun <T> PhotoWidgetTabs(
    options: List<SelectOption<T>>,
    value: T,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
    ) {
        options.forEach { option ->
            val selected = option.value == value
            Box(
                modifier = Modifier
                    .clickable { onValueChange(option.value) }
                    .padding(horizontal = 6.dp, vertical = 10.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Text(
                    text = option.label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (selected) {
                    Box(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(2.dp)),
                    )
                }
            }
        }
    }
}
