package com.photowidget.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.core.IconButtonVariant
import com.photowidget.ui.components.core.PhotoWidgetIconButton

/** Header shared by "Home Screen - Empty.dc.html" and "Home Screen - Widget List.dc.html": wordmark + settings gear. */
@Composable
fun HomeHeader(onOpenSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge.copy(fontStyle = FontStyle.Italic),
            color = MaterialTheme.colorScheme.onSurface,
        )
        PhotoWidgetIconButton(
            icon = Icons.Rounded.Settings,
            contentDescription = stringResource(R.string.app_settings_title),
            onClick = onOpenSettings,
            variant = IconButtonVariant.Ghost,
        )
    }
}
