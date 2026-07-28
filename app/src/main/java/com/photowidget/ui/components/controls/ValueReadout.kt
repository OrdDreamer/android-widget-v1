package com.photowidget.ui.components.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.photowidget.ui.theme.RadiusMd

enum class ValueReadoutTone { Neutral, Accent }

/** Design-system ValueReadout (controls/ValueReadout.jsx): a value + small caption label stacked in a chip. */
@Composable
fun PhotoWidgetValueReadout(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    tone: ValueReadoutTone = ValueReadoutTone.Neutral,
) {
    val background = if (tone == ValueReadoutTone.Accent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(background, RoundedCornerShape(RadiusMd))
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(text = value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
