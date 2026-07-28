package com.photowidget.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.WidgetImagePreview
import com.photowidget.ui.components.core.AdBanner
import com.photowidget.ui.components.core.ButtonSize
import com.photowidget.ui.components.core.ButtonVariant
import com.photowidget.ui.components.core.CardPadding
import com.photowidget.ui.components.core.CardVariant
import com.photowidget.ui.components.core.PhotoWidgetButton
import com.photowidget.ui.components.core.PhotoWidgetCard
import com.photowidget.ui.photoWidgetNavigationBarPadding
import com.photowidget.ui.photoWidgetSafeAreaPadding
import com.photowidget.ui.theme.PhotoWidgetTheme

/** "Home Screen - Widget List.dc.html". */
@Composable
fun WidgetListScreen(
    widgetIds: IntArray,
    widgetItems: Map<Int, WidgetListItem>,
    onEditWidget: (Int) -> Unit,
    onResetWidget: (Int) -> Unit,
    onPinWidget: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .photoWidgetSafeAreaPadding(),
    ) {
        HomeHeader(onOpenSettings = onOpenSettings)

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(widgetIds.toList(), key = { it }) { widgetId ->
                WidgetRow(
                    item = widgetItems[widgetId],
                    onEdit = { onEditWidget(widgetId) },
                    onReset = { onResetWidget(widgetId) },
                )
            }
        }

        Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            PhotoWidgetButton(
                text = stringResource(R.string.pin_widget),
                onClick = onPinWidget,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                icon = Icons.Rounded.Add,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        AdBanner(modifier = Modifier.photoWidgetNavigationBarPadding())
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WidgetRow(item: WidgetListItem?, onEdit: () -> Unit, onReset: () -> Unit) {
    PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Small, modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            WidgetThumbnail(item = item)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = item?.title ?: stringResource(R.string.widget_number, 0),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = if (item?.config?.imageUri == null) {
                        stringResource(R.string.no_photo_selected)
                    } else {
                        item.sizeLabel
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp),
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 12.dp),
                ) {
                    PhotoWidgetButton(text = stringResource(R.string.edit_widget), onClick = onEdit, variant = ButtonVariant.Primary, size = ButtonSize.Small)
                    PhotoWidgetButton(text = stringResource(R.string.delete_widget_short), onClick = onReset, variant = ButtonVariant.Secondary, size = ButtonSize.Small)
                }
            }
        }
    }
}

@Composable
private fun WidgetThumbnail(item: WidgetListItem?, modifier: Modifier = Modifier) {
    val config = item?.config
    val shape = when (config?.shape) {
        WidgetShape.CIRCLE -> RoundedCornerShape(percent = 50)
        WidgetShape.ROUNDED_RECT, null -> RoundedCornerShape(14.dp)
        WidgetShape.RECTANGLE -> RoundedCornerShape(0.dp)
    }
    Box(
        modifier = modifier
            .size(108.dp)
            .clip(shape)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, shape)
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
            Icon(
                imageVector = Icons.Rounded.Image,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(28.dp),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WidgetListScreenPreview() {
    val items = mapOf(
        1 to WidgetListItem(title = "Мама", sizeLabel = "3×3", config = WidgetConfig(widgetNumber = 1)),
        2 to WidgetListItem(title = "Віджет #2", sizeLabel = "2×2", config = WidgetConfig(widgetNumber = 2, imageUri = null)),
    )
    PhotoWidgetTheme {
        WidgetListScreen(
            widgetIds = items.keys.toIntArray(),
            widgetItems = items,
            onEditWidget = {},
            onResetWidget = {},
            onPinWidget = {},
            onOpenSettings = {},
        )
    }
}
