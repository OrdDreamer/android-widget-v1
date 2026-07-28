package com.photowidget.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photowidget.R

/**
 * Plain footer placeholder shared by every screen mock's bottom bar. Not part of the 27-component
 * design-system library (no dedicated `.jsx` exists for it) — kept as a one-off internal composable.
 * Full-width, 64dp tall per "Home Screen - Widget List.dc.html"'s `bannerBase` — swap the Text for
 * the real ad SDK view without touching this footprint when a real banner is wired up.
 */
@Composable
fun AdBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.ad_banner_placeholder),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
