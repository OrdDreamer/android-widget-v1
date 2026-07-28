package com.photowidget.ui.screens.appsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.core.CardPadding
import com.photowidget.ui.components.core.CardVariant
import com.photowidget.ui.components.core.PhotoWidgetCard
import com.photowidget.ui.photoWidgetSafeAreaPadding
import com.photowidget.ui.screens.common.ScreenHeader
import com.photowidget.ui.theme.PhotoWidgetTheme

/**
 * Reached from App Settings → Language. No locale switching exists yet (the app is
 * Ukrainian-only, no `values-uk` resource set) — this route exists so Language is a real
 * destination rather than the pre-rebuild TODO stub, showing the current (only) option.
 */
@Composable
fun LanguageScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .photoWidgetSafeAreaPadding(),
    ) {
        ScreenHeader(title = stringResource(R.string.settings_language), onBack = onBack)
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
            PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Medium, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.settings_language_system_default),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LanguageScreenPreview() {
    PhotoWidgetTheme {
        LanguageScreen(onBack = {})
    }
}
