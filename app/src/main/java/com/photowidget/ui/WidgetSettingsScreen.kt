package com.photowidget.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.RotateLeft
import androidx.compose.material.icons.automirrored.filled.RotateRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.data.FrameStyle
import com.photowidget.data.ImageAlignment
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetClickAction
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.components.AdBannerPlaceholder
import com.photowidget.ui.components.GradientPrimaryButton
import com.photowidget.ui.theme.screenBackgroundBrush

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetSettingsScreen(
    initialConfig: WidgetConfig,
    onSave: (WidgetConfig) -> Unit,
    onCancel: (() -> Unit)? = null,
    saveButtonLabel: String = stringResource(R.string.save),
) {
    val context = LocalContext.current
    var config by remember(initialConfig) { mutableStateOf(initialConfig) }
    var previewRetryKey by remember { mutableIntStateOf(0) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION,
                )
            } catch (_: SecurityException) {
                // Photo Picker URI may not support persistable permission on all devices.
            }
            config = config.copy(imageUri = uri.toString())
            previewRetryKey++
        }
    }

    val content: @Composable (PaddingValues) -> Unit = { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(screenBackgroundBrush(isSystemInDarkTheme()))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp),
        ) {
            WidgetPreview(
                config = config,
                retryKey = previewRetryKey,
                onRetry = { previewRetryKey++ },
                onChoosePhoto = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
            )

            FilledTonalButton(
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
            ) {
                Text(stringResource(R.string.select_photo))
            }

            if (config.imageUri == null) {
                Text(
                    text = stringResource(R.string.photo_stays_on_device),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            FrameStyleSelector(
                selected = config.frameStyle,
                onSelected = { config = config.copy(frameStyle = it) },
            )

            Column {
                OutlinedTextField(
                    value = config.displayName.orEmpty(),
                    onValueChange = {
                        config = config.copy(displayName = it.take(40).ifBlank { null })
                    },
                    label = { Text(stringResource(R.string.widget_display_name)) },
                    placeholder = { Text(stringResource(R.string.widget_display_name_hint)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = stringResource(
                        R.string.display_name_counter,
                        config.displayName.orEmpty().length,
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                )
            }

            if (config.imageUri != null) {
                Text(
                    text = stringResource(R.string.image_rotation),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedButton(
                        onClick = {
                            config = config.copy(rotationDegrees = config.rotationDegrees - 90)
                        },
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.RotateLeft,
                            contentDescription = stringResource(R.string.rotate_counterclockwise),
                        )
                    }
                    Text(
                        text = stringResource(
                            R.string.rotation_degrees,
                            normalizeRotation(config.rotationDegrees),
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.width(48.dp),
                        textAlign = TextAlign.Center,
                    )
                    OutlinedButton(
                        onClick = {
                            config = config.copy(rotationDegrees = config.rotationDegrees + 90)
                        },
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.RotateRight,
                            contentDescription = stringResource(R.string.rotate_clockwise),
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.image_alignment),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                ImageAlignmentSelector(
                    selected = config.imageAlignment,
                    onSelected = { config = config.copy(imageAlignment = it) },
                )
            }

            Text(
                text = stringResource(R.string.scale_mode),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                ScaleMode.entries.forEachIndexed { index, mode ->
                    SegmentedButton(
                        selected = config.scaleMode == mode,
                        onClick = { config = config.copy(scaleMode = mode) },
                        modifier = Modifier.height(48.dp),
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = ScaleMode.entries.size,
                        ),
                    ) {
                        Text(
                            when (mode) {
                                ScaleMode.COVER -> stringResource(R.string.cover)
                                ScaleMode.CONTAIN -> stringResource(R.string.contain)
                            },
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Text(
                text = stringResource(R.string.shape),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                WidgetShape.entries.forEachIndexed { index, shape ->
                    SegmentedButton(
                        selected = config.shape == shape,
                        onClick = { config = config.copy(shape = shape) },
                        modifier = Modifier.height(48.dp),
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = WidgetShape.entries.size,
                        ),
                    ) {
                        Text(
                            when (shape) {
                                WidgetShape.RECTANGLE -> stringResource(R.string.shape_rectangle)
                                WidgetShape.ROUNDED_RECT -> stringResource(R.string.shape_rounded)
                                WidgetShape.CIRCLE -> stringResource(R.string.shape_circle)
                            },
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            if (config.shape == WidgetShape.ROUNDED_RECT) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.corner_rounding),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = stringResource(
                            R.string.corner_rounding_value,
                            cornerRoundingLabel(config.cornerRadiusDp),
                            config.cornerRadiusDp,
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Slider(
                    value = config.cornerRadiusDp.toFloat(),
                    onValueChange = { config = config.copy(cornerRadiusDp = it.toInt()) },
                    valueRange = 0f..48f,
                    steps = 47,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Text(
                text = stringResource(R.string.widget_system_rounding_note),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = stringResource(R.string.widget_click_behavior),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                WidgetClickAction.entries.forEachIndexed { index, clickAction ->
                    SegmentedButton(
                        selected = config.clickAction == clickAction,
                        onClick = { config = config.copy(clickAction = clickAction) },
                        modifier = Modifier.height(56.dp),
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = WidgetClickAction.entries.size,
                        ),
                    ) {
                        Text(
                            when (clickAction) {
                                WidgetClickAction.DECORATIVE ->
                                    stringResource(R.string.click_decorative)
                                WidgetClickAction.OPEN_APP ->
                                    stringResource(R.string.click_open_app)
                                WidgetClickAction.OPEN_WIDGET_SETTINGS ->
                                    stringResource(R.string.click_open_widget_settings)
                            },
                            textAlign = TextAlign.Center,
                            minLines = 2,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }

            AdBannerPlaceholder()

            GradientPrimaryButton(
                text = saveButtonLabel,
                onClick = { onSave(config) },
            )

            if (onCancel != null) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    if (onCancel != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.configure_widget)) },
                    navigationIcon = {
                        IconButton(onClick = onCancel) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.navigate_back),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            },
            content = content,
        )
    } else {
        content(PaddingValues(0.dp))
    }
}

@Composable
private fun FrameStyleSelector(
    selected: FrameStyle,
    onSelected: (FrameStyle) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = stringResource(R.string.frame_style),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.frame_style_see_all),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FrameStyle.entries.forEach { style ->
                val active = selected == style
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onSelected(style) },
                ) {
                    Box(
                        modifier = Modifier.size(62.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    BorderStroke(
                                        2.5.dp,
                                        if (active) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            Color.Transparent
                                        },
                                    ),
                                    RoundedCornerShape(12.dp),
                                )
                                .padding(2.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Color(0xFFD4C4F5), Color(0xFFE0B8F0)),
                                    ),
                                ),
                        )
                        if (active) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 4.dp, y = (-4).dp)
                                    .size(17.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(12.dp),
                                )
                            }
                        }
                    }
                    Text(
                        text = frameStyleLabel(style),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun cornerRoundingLabel(dp: Int): String = when {
    dp <= 8 -> stringResource(R.string.corner_rounding_soft)
    dp <= 20 -> stringResource(R.string.corner_rounding_medium)
    else -> stringResource(R.string.corner_rounding_strong)
}

@Composable
private fun ImageAlignmentSelector(
    selected: ImageAlignment,
    onSelected: (ImageAlignment) -> Unit,
) {
    val rows = listOf(
        listOf(ImageAlignment.TOP_LEFT, ImageAlignment.TOP, ImageAlignment.TOP_RIGHT),
        listOf(ImageAlignment.LEFT, ImageAlignment.CENTER, ImageAlignment.RIGHT),
        listOf(ImageAlignment.BOTTOM_LEFT, ImageAlignment.BOTTOM, ImageAlignment.BOTTOM_RIGHT),
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            rows.forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    row.forEach { alignment ->
                        val isSelected = selected == alignment
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    },
                                )
                                .border(
                                    BorderStroke(
                                        1.dp,
                                        if (isSelected) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.outline
                                        },
                                    ),
                                    RoundedCornerShape(8.dp),
                                )
                                .clickable { onSelected(alignment) },
                        )
                    }
                }
            }
        }
    }
}

private fun normalizeRotation(degrees: Int): Int {
    return ((degrees % 360) + 360) % 360
}

@Composable
private fun WidgetPreview(
    config: WidgetConfig,
    retryKey: Int,
    onRetry: () -> Unit,
    onChoosePhoto: () -> Unit,
) {
    val cornerRadius = when (config.shape) {
        WidgetShape.RECTANGLE -> 4.dp
        WidgetShape.ROUNDED_RECT -> config.cornerRadiusDp.dp
        WidgetShape.CIRCLE -> 999.dp
    }
    val shape = RoundedCornerShape(cornerRadius)
    val outlineColor = MaterialTheme.colorScheme.outline

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (config.imageUri == null) {
                    Modifier.drawBehind {
                        val radiusPx = cornerRadius.toPx()
                        drawRoundRect(
                            color = outlineColor,
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(12f, 10f),
                                    0f,
                                ),
                            ),
                            cornerRadius = CornerRadius(radiusPx, radiusPx),
                        )
                    }
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (config.imageUri != null) {
            WidgetImagePreview(
                imageUri = config.imageUri,
                rotationDegrees = config.rotationDegrees,
                imageAlignment = config.imageAlignment,
                scaleMode = config.scaleMode,
                frameStyle = config.frameStyle,
                modifier = Modifier.fillMaxSize(),
                retryKey = retryKey,
                errorContent = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(20.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .border(
                                    BorderStroke(2.dp, MaterialTheme.colorScheme.error),
                                    CircleShape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                        Text(
                            text = stringResource(R.string.photo_load_error),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                        TextButton(onClick = onRetry) {
                            Text(stringResource(R.string.photo_load_retry))
                        }
                    }
                },
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.clickable(onClick = onChoosePhoto),
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(28.dp),
                )
                Text(
                    text = stringResource(R.string.no_photo_selected),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
