package com.photowidget.ui.components.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.photoWidgetShadow

/** Design-system Slider (controls/Slider.jsx): pill track/thumb recolored on top of M3's [Slider]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoWidgetSlider(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    valueRange: IntRange = 0..100,
) {
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = androidx.compose.ui.graphics.Color.White,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.outlineVariant,
            ),
            thumb = {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .size(20.dp)
                        .photoWidgetShadow(com.photowidget.ui.theme.PhotoWidgetElevation.md, CircleShape)
                        .background(androidx.compose.ui.graphics.Color.White, CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                )
            },
            track = { state ->
                Column {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(percent = 50)),
                    ) {
                        val fraction = (state.value - state.valueRange.start) / (state.valueRange.endInclusive - state.valueRange.start)
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction.coerceIn(0f, 1f))
                                .height(6.dp)
                                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(percent = 50)),
                        )
                    }
                }
            },
        )
    }
}
