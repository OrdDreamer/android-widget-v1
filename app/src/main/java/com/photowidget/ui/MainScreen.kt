package com.photowidget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.photowidget.ui.theme.brandContainerBrush
import com.photowidget.ui.theme.brandTitleBrush
import com.photowidget.ui.theme.heroCardBrush
import com.photowidget.ui.theme.screenBackgroundBrush
import com.photowidget.ui.photoWidgetNavigationBarPadding
import com.photowidget.ui.photoWidgetSafeAreaPadding

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
                        height = 52.dp,
                        leading = { GradientPlusBadge() },
                    )

                    Text(
                        text = stringResource(R.string.active_widgets).uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 6.dp),
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
                .background(brandContainerBrush())
                .clickable(onClick = onOpenSettings),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = stringResource(R.string.app_settings_title),
                tint = MaterialTheme.colorScheme.primary,
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
                .background(brandPrimaryBrushLike()),
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

@Composable
private fun brandPrimaryBrushLike() = Brush.linearGradient(
    colors = listOf(Color(0xFF6B9AFF), Color(0xFFC24DFF)),
)

@Composable
private fun EmptyStateHero() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
            .shadow(16.dp, RoundedCornerShape(28.dp), clip = false)
            .clip(RoundedCornerShape(28.dp))
            .background(heroCardBrush()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.25f), Color.Transparent),
                        radius = 500f,
                    ),
                ),
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(-7f)
                .size(width = 151.dp, height = 139.dp)
                .shadow(16.dp, RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 26.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF4A3A7A),
                                Color(0xFF9B4DFF),
                                Color(0xFFFFB86B),
                            ),
                        ),
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(x = 20.dp, y = 28.dp)
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF7D98A)),
                )
            }
        }
    }
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
            .shadow(6.dp, RoundedCornerShape(18.dp), clip = false)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        WidgetListThumbnail(config = config)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item?.title ?: stringResource(R.string.widget_number, 0),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
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
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(brandContainerBrush())
                .clickable(onClick = onEdit),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(R.string.edit_widget),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(18.dp),
            )
        }
        OutlinedButton(
            onClick = onReset,
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
        ) {
            Text(
                text = stringResource(R.string.delete_widget_short),
                style = MaterialTheme.typography.labelLarge,
            )
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
        WidgetShape.RECTANGLE, null -> RoundedCornerShape(4.dp)
        WidgetShape.ROUNDED_RECT -> RoundedCornerShape((config.cornerRadiusDp / 2).coerceAtLeast(4).dp)
        WidgetShape.CIRCLE -> CircleShape
    }

    Box(
        modifier = Modifier
            .size(54.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        if (config?.imageUri != null) {
            WidgetImagePreview(
                imageUri = config.imageUri,
                rotationDegrees = config.rotationDegrees,
                imageAlignment = config.imageAlignment,
                scaleMode = config.scaleMode,
                frameStyle = config.frameStyle,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Text(
                text = "#${config?.widgetNumber ?: "?"}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
