package com.photowidget.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.ui.components.core.AdBanner
import com.photowidget.ui.components.core.ButtonSize
import com.photowidget.ui.components.core.ButtonVariant
import com.photowidget.ui.components.core.PhotoWidgetButton
import com.photowidget.ui.theme.Cream50
import com.photowidget.ui.theme.PhotoWidgetElevation
import com.photowidget.ui.theme.PhotoWidgetTheme
import com.photowidget.ui.theme.Sage300
import com.photowidget.ui.theme.Terracotta300
import com.photowidget.ui.theme.photoWidgetShadow
import com.photowidget.ui.photoWidgetNavigationBarPadding
import com.photowidget.ui.photoWidgetSafeAreaPadding

/** "Home Screen - Empty.dc.html". */
@Composable
fun HomeEmptyScreen(onPinWidget: () -> Unit, onOpenSettings: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .photoWidgetSafeAreaPadding(),
    ) {
        HomeHeader(onOpenSettings = onOpenSettings)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            HeroIllustration(modifier = Modifier.fillMaxWidth().aspectRatio(4f / 3f))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 300.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Step(number = 1, text = stringResource(R.string.empty_step_1))
                    Step(number = 2, text = stringResource(R.string.empty_step_2))
                    Step(number = 3, text = stringResource(R.string.empty_step_3))
                }

                Box(
                    modifier = Modifier
                        .widthIn(max = 300.dp)
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                ) {
                    PhotoWidgetButton(
                        text = stringResource(R.string.pin_widget),
                        onClick = onPinWidget,
                        variant = ButtonVariant.Primary,
                        size = ButtonSize.Large,
                        icon = Icons.Rounded.Add,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        AdBanner(modifier = Modifier.photoWidgetNavigationBarPadding())
    }
}

@Composable
private fun Step(number: Int, text: String) {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 1.dp)
                .width(22.dp)
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.primaryContainer, androidx.compose.foundation.shape.CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * "Home Screen - Empty.dc.html" leaves this hero as an unresolved `<image-slot>` placeholder (no
 * asset was provided) — reproduced here as a native abstract composition (tilted polaroid over a
 * card) rather than a bitmap, consistent with the launch screen's own polaroid motif.
 */
@Composable
private fun HeroIllustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(120f / 146f)
                .rotate(-6f)
                .photoWidgetShadow(PhotoWidgetElevation.md, RoundedCornerShape(6.dp))
                .background(Cream50, RoundedCornerShape(6.dp))
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 24.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.linearGradient(listOf(Terracotta300, Sage300)), RoundedCornerShape(3.dp)),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeEmptyScreenPreview() {
    PhotoWidgetTheme {
        HomeEmptyScreen(onPinWidget = {}, onOpenSettings = {})
    }
}
