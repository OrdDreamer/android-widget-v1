package com.photowidget.ui.components.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.RadiusSm
import com.photowidget.ui.theme.Terracotta700

data class SliderPreset(val value: Int, val label: String)

/** Design-system SliderPresets (controls/SliderPresets.jsx): [PhotoWidgetSlider] + a preset pill row. */
@Composable
fun PhotoWidgetSliderPresets(
    value: Int,
    onValueChange: (Int) -> Unit,
    presets: List<SliderPreset>,
    modifier: Modifier = Modifier,
    label: String? = null,
    valueRange: IntRange = 0..100,
) {
    Column(modifier = modifier) {
        PhotoWidgetSlider(value = value, onValueChange = onValueChange, label = label, valueRange = valueRange)
        Row(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            presets.forEach { preset ->
                val selected = preset.value == value
                val shape = RoundedCornerShape(RadiusSm)
                Text(
                    text = preset.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (selected) Terracotta700 else MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onValueChange(preset.value) }
                        .background(
                            if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                            shape,
                        )
                        .border(1.dp, if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, shape)
                        .padding(vertical = 6.dp),
                )
            }
        }
    }
}
