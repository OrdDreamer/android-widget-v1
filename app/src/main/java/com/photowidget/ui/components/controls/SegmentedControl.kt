package com.photowidget.ui.components.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.photowidget.ui.components.forms.SelectOption
import com.photowidget.ui.theme.DurationBase
import com.photowidget.ui.theme.calmTween
import com.photowidget.ui.theme.photoWidgetShadow
import com.photowidget.ui.theme.surfaceRaised

/** Design-system SegmentedControl (controls/SegmentedControl.jsx): animated pill thumb over equal-width segments. */
@Composable
fun <T> PhotoWidgetSegmentedControl(
    options: List<SelectOption<T>>,
    value: T,
    onValueChange: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedIndex = options.indexOfFirst { it.value == value }.coerceAtLeast(0)
    val trackShape = RoundedCornerShape(percent = 50)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, trackShape)
            .padding(4.dp),
    ) {
        val segmentWidth = maxWidth / options.size
        val offset by animateFloatAsState(
            targetValue = selectedIndex.toFloat(),
            animationSpec = calmTween(DurationBase),
            label = "segmentedThumbOffset",
        )

        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .offset(x = segmentWidth * offset)
                .width(segmentWidth)
                .height(32.dp)
                .photoWidgetShadow(com.photowidget.ui.theme.PhotoWidgetElevation.sm, RoundedCornerShape(percent = 50))
                .background(surfaceRaised(), RoundedCornerShape(percent = 50)),
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            options.forEachIndexed { index, option ->
                val selected = index == selectedIndex
                Text(
                    text = option.label,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onValueChange(option.value) }
                        .padding(vertical = 8.dp),
                )
            }
        }
    }
}
