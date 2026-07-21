package com.photowidget.ui

import androidx.compose.foundation.Image as ComposeImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.photowidget.R
import com.photowidget.data.FrameStyle
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.components.AdBannerPlaceholder
import com.photowidget.ui.components.GradientPlusBadge
import com.photowidget.ui.components.GradientPrimaryButton
import com.photowidget.ui.photoWidgetNavigationBarPadding
import com.photowidget.ui.photoWidgetSafeAreaPadding
import com.photowidget.ui.theme.brandIconTint
import com.photowidget.ui.theme.brandTitleBrush
import com.photowidget.ui.theme.iconBoxBrush
import com.photowidget.ui.theme.listThumbnailPlaceholderBorderColor
import com.photowidget.ui.theme.screenBackgroundBrush
import com.photowidget.ui.theme.stepBadgeBrush

data class WidgetListItem(
    val title: String,
    val sizeLabel: String,
    val config: WidgetConfig,
)

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    widgetIds: IntArray,
    widgetItems: Map<Int, WidgetListItem>,
    onEditWidget: (Int) -> Unit,
    onDeleteWidget: (Int) -> Unit,
    onPinWidget: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val dark = isSystemInDarkTheme()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackgroundBrush(dark))
            .photoWidgetSafeAreaPadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MainHeader(onOpenSettings = onOpenSettings)

            if (widgetIds.isEmpty()) {
                EmptyWidgetsState(
                    onPinWidget = onPinWidget,
                    modifier = Modifier.padding(horizontal = 22.dp),
                )
            } else {
                Column(
                    modifier = Modifier.padding(horizontal = 22.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    GradientPrimaryButton(
                        text = stringResource(R.string.pin_widget),
                        onClick = onPinWidget,
                        height = 54.dp,
                        leading = { GradientPlusBadge() },
                    )

                    Text(
                        text = stringResource(R.string.active_widgets).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                    )

                    widgetIds.forEach { widgetId ->
                        WidgetListRow(
                            item = widgetItems[widgetId],
                            onEdit = { onEditWidget(widgetId) },
                            onReset = { onDeleteWidget(widgetId) },
                        )
                    }
                }
            }
        }

        AdBannerPlaceholder(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 12.dp)
                .photoWidgetNavigationBarPadding(),
        )
    }
}

@Composable
private fun MainHeader(onOpenSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp, top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.app_name) + " ✨",
                style = MaterialTheme.typography.headlineMedium.copy(
                    brush = brandTitleBrush(),
                    fontWeight = FontWeight.Black,
                ),
            )
            Text(
                text = stringResource(R.string.app_tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(iconBoxBrush())
                .clickable(onClick = onOpenSettings),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.app_settings_title),
                tint = brandIconTint(),
            )
        }
    }
}

@Composable
private fun EmptyWidgetsState(
    onPinWidget: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        EmptyStateHero()

        Text(
            text = stringResource(R.string.empty_headline),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = stringResource(R.string.empty_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        EmptyStep(number = 1, text = stringResource(R.string.empty_step_1))
        EmptyStep(number = 2, text = stringResource(R.string.empty_step_2))
        EmptyStep(number = 3, text = stringResource(R.string.empty_step_3))

        Text(
            text = stringResource(R.string.empty_pin_fallback),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(4.dp))

        GradientPrimaryButton(
            text = stringResource(R.string.pin_widget),
            onClick = onPinWidget,
            height = 58.dp,
            leading = { GradientPlusBadge() },
        )
    }
}

@Composable
private fun EmptyStep(number: Int, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(9.dp))
                .background(stepBadgeBrush()),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 3.dp),
        )
    }
}

/**
 * Empty-state hero illustration. This is a raster asset (`empty_state_hero.png` /
 * `drawable-night-nodpi/empty_state_hero.png`), not a native re-creation: it bakes the card,
 * the tilted polaroid, and the dusk-skyline illustration into one image at the mock's exact
 * proportions (368x230 card, 151x139 polaroid, generated from the same verified CSS geometry
 * used previously), so the polaroid-to-card ratio can't drift on devices narrower or wider than
 * the mock's 412dp frame the way it did when the card and the polaroid were sized independently
 * in Compose.
 */
@Composable
private fun EmptyStateHero() {
    ComposeImage(
        painter = painterResource(R.drawable.empty_state_hero),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            // Mock's hero card is 368x230 (412dp mock frame minus 22dp margins) — an
            // aspect ratio, not a fixed height, so the polaroid-to-card proportion the raster
            // was rendered at survives regardless of the actual device width.
            .aspectRatio(368f / 230f)
            .clip(RoundedCornerShape(28.dp)),
    )
}

@Composable
private fun WidgetListRow(
    item: WidgetListItem?,
    onEdit: () -> Unit,
    onReset: () -> Unit,
) {
    val config = item?.config
    val styleLabel = frameStyleLabel(config?.frameStyle ?: FrameStyle.POLAROID)
    val meta = "${item?.sizeLabel ?: "1×1"} · $styleLabel"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp), clip = false)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WidgetListThumbnail(config = config)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = item?.title ?: stringResource(R.string.widget_number, 0),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = meta,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconBoxBrush())
                    .clickable(onClick = onEdit),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.edit_widget),
                    tint = brandIconTint(),
                    modifier = Modifier.size(18.dp),
                )
            }
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(18.dp),
                    )
                    .clickable(onClick = onReset)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.delete_widget_short),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun frameStyleLabel(style: FrameStyle): String = when (style) {
    FrameStyle.CLASSIC -> stringResource(R.string.frame_classic)
    FrameStyle.POLAROID -> stringResource(R.string.frame_polaroid)
    FrameStyle.MINIMAL -> stringResource(R.string.frame_minimal)
    FrameStyle.VINTAGE -> stringResource(R.string.frame_vintage)
}

@Composable
fun WidgetListThumbnail(config: WidgetConfig?) {
    val shape = when (config?.shape) {
        WidgetShape.RECTANGLE, null -> RoundedCornerShape(10.dp)
        WidgetShape.ROUNDED_RECT -> RoundedCornerShape((config.cornerRadiusDp / 2).coerceAtLeast(10).dp)
        WidgetShape.CIRCLE -> CircleShape
    }
    val hasImage = config?.imageUri != null

    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(shape)
            .then(
                if (hasImage) {
                    Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                } else {
                    Modifier
                        .background(iconBoxBrush())
                        .dashedBorder(
                            width = 2.dp,
                            color = listThumbnailPlaceholderBorderColor(),
                            shape = shape,
                        )
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (hasImage) {
            WidgetImagePreview(
                imageUri = config.imageUri,
                rotationDegrees = config.rotationDegrees,
                imageAlignment = config.imageAlignment,
                scaleMode = config.scaleMode,
                frameStyle = config.frameStyle,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.Image,
                contentDescription = null,
                tint = brandIconTint(),
                modifier = Modifier.size(24.dp),
            )
        }
    }
}
