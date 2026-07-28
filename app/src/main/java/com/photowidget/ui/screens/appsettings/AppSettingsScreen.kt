package com.photowidget.ui.screens.appsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.PrivacyTip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.core.AdBanner
import com.photowidget.ui.components.core.CardPadding
import com.photowidget.ui.components.core.CardVariant
import com.photowidget.ui.components.core.PhotoWidgetCard
import com.photowidget.ui.photoWidgetNavigationBarPadding
import com.photowidget.ui.photoWidgetSafeAreaPadding
import com.photowidget.ui.screens.common.ScreenHeader

/** "App Settings.dc.html". */
@Composable
fun AppSettingsScreen(
    onBack: () -> Unit,
    onNavigateLanguage: () -> Unit,
    onNavigateAbout: () -> Unit,
    onNavigatePrivacy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .photoWidgetSafeAreaPadding(),
    ) {
        ScreenHeader(title = stringResource(R.string.app_settings_title), onBack = onBack)

        Column(modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 20.dp)) {
            PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.None, modifier = Modifier.fillMaxWidth()) {
                Column {
                    SettingsRow(icon = Icons.Rounded.Language, label = stringResource(R.string.settings_language), onClick = onNavigateLanguage)
                    SettingsRow(icon = Icons.Rounded.Info, label = stringResource(R.string.settings_about), onClick = onNavigateAbout)
                    SettingsRow(icon = Icons.Rounded.PrivacyTip, label = stringResource(R.string.settings_privacy), onClick = onNavigatePrivacy, showDivider = false)
                }
            }
        }

        AdBanner(modifier = Modifier.photoWidgetNavigationBarPadding())
    }
}

@Composable
private fun SettingsRow(icon: ImageVector, label: String, onClick: () -> Unit, showDivider: Boolean = true) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
        }
        if (showDivider) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
            )
        }
    }
}
