package com.photowidget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.components.AdBannerPlaceholder

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
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (widgetIds.isEmpty()) {
                EmptyWidgetsState(onPinWidget = onPinWidget)
            } else {
                Button(
                    onClick = onPinWidget,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                ) {
                    Text(stringResource(R.string.pin_widget))
                }

                Text(
                    text = stringResource(R.string.active_widgets),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp),
                )

                widgetIds.forEach { widgetId ->
                    val item = widgetItems[widgetId]
                    WidgetListRow(
                        item = item,
                        onEdit = { onEditWidget(widgetId) },
                        onReset = { onDeleteWidget(widgetId) },
                    )
                }
            }
        }

        AdBannerPlaceholder(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
        )
    }
}

@Composable
private fun EmptyWidgetsState(onPinWidget: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        EmptyStateIllustration()

        Text(
            text = stringResource(R.string.empty_headline),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = stringResource(R.string.empty_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = (-10).dp),
        )

        EmptyStep(number = 1, text = stringResource(R.string.empty_step_1))
        EmptyStep(number = 2, text = stringResource(R.string.empty_step_2))
        EmptyStep(number = 3, text = stringResource(R.string.empty_step_3))

        Text(
            text = stringResource(R.string.empty_pin_fallback),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onPinWidget,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
        ) {
            Text(stringResource(R.string.pin_widget))
        }
    }
}

@Composable
private fun EmptyStep(number: Int, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
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
private fun EmptyStateIllustration() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Box(
            modifier = Modifier
                .padding(start = 24.dp, top = 28.dp)
                .size(width = 180.dp, height = 150.dp)
                .clip(RoundedCornerShape(48))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)),
        )
        Text(
            text = stringResource(R.string.empty_illustration_wallpaper),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 24.dp, top = 184.dp),
        )
        Text(
            text = stringResource(R.string.empty_illustration_widget),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 28.dp, bottom = 136.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 8.dp, vertical = 3.dp),
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 28.dp, bottom = 28.dp)
                .size(104.dp)
                .shadow(6.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(3.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
        )
    }
}

@Composable
private fun WidgetListRow(
    item: WidgetListItem?,
    onEdit: () -> Unit,
    onReset: () -> Unit,
) {
    val config = item?.config
    val shapeLabel = when (config?.shape) {
        WidgetShape.RECTANGLE, null -> stringResource(R.string.shape_rectangle)
        WidgetShape.ROUNDED_RECT -> stringResource(R.string.shape_rounded)
        WidgetShape.CIRCLE -> stringResource(R.string.shape_circle)
    }
    val meta = buildString {
        append(item?.sizeLabel ?: "1x1")
        append(" · ")
        append(shapeLabel)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        WidgetListThumbnail(config = config)
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item?.title ?: stringResource(R.string.widget_number, 0),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = meta,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        FilledTonalButton(
            onClick = onEdit,
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
        ) {
            Text(
                text = stringResource(R.string.edit_widget),
                style = MaterialTheme.typography.labelLarge,
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
fun WidgetListThumbnail(config: WidgetConfig?) {
    val shape = when (config?.shape) {
        WidgetShape.RECTANGLE, null -> RoundedCornerShape(4.dp)
        WidgetShape.ROUNDED_RECT -> RoundedCornerShape((config.cornerRadiusDp / 2).coerceAtLeast(4).dp)
        WidgetShape.CIRCLE -> CircleShape
    }

    Box(
        modifier = Modifier
            .size(52.dp)
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
