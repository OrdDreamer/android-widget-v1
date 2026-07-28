package com.photowidget.ui.components.forms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.photowidget.ui.theme.Cream50

/** Design-system Switch (forms/Switch.jsx), recolored via SwitchDefaults per jetpack-compose.md. */
@Composable
fun PhotoWidgetSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            checkedThumbColor = Cream50,
            uncheckedTrackColor = MaterialTheme.colorScheme.outline,
            uncheckedThumbColor = Cream50,
            uncheckedBorderColor = MaterialTheme.colorScheme.outline,
        ),
    )
}
