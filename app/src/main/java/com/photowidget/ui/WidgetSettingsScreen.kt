package com.photowidget.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.RotateLeft
import androidx.compose.material.icons.automirrored.filled.RotateRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.East
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.RoundedCorner
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.filled.SouthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.West
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.photowidget.R
import com.photowidget.data.FrameStyle
import com.photowidget.data.ImageAlignment
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetClickAction
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.components.AdBannerPlaceholder
import com.photowidget.ui.components.GradientPrimaryButton
import com.photowidget.ui.components.MiniSegmentedPill
import com.photowidget.ui.components.SettingsCard
import com.photowidget.ui.components.SettingsCardDivider
import com.photowidget.ui.components.SettingsCardRow
import com.photowidget.ui.theme.accentGradientBrush
import com.photowidget.ui.theme.choosePhotoTextColor
import com.photowidget.ui.theme.dashedBoxBackgroundBrush
import com.photowidget.ui.theme.dashedBoxBorderColor
import com.photowidget.ui.theme.errorAccentColor
import com.photowidget.ui.theme.errorContainerColor
import com.photowidget.ui.theme.frameSwatchBrush
import com.photowidget.ui.theme.iconBoxBrush
import com.photowidget.ui.theme.pillTrackBackgroundColor
import com.photowidget.ui.theme.screenBackgroundBrush

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
    var loadFailed by remember(config.imageUri) { mutableStateOf(false) }

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
    val launchPicker = {
        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundBrush(isSystemInDarkTheme()))
            .photoWidgetSafeAreaPadding(),
    ) {
        if (onCancel != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onCancel),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_back),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(26.dp),
                    )
                }
                Text(
                    text = stringResource(R.string.configure_widget),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp, bottom = 20.dp)
                .photoWidgetNavigationBarPadding(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            WidgetPreviewBox(
                config = config,
                retryKey = previewRetryKey,
                loadFailed = loadFailed,
                onLoadFailed = { loadFailed = true },
                onChoosePhoto = launchPicker,
            )

            if (loadFailed) {
                PhotoErrorNotice(
                    onRetry = {
                        loadFailed = false
                        previewRetryKey++
                    },
                )
            }

            ChoosePhotoButton(onClick = launchPicker)

            if (config.imageUri == null) {
                Text(
                    text = stringResource(R.string.photo_stays_on_device),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            SettingsCard {
                FrameStyleSelector(
                    selected = config.frameStyle,
                    onSelected = { config = config.copy(frameStyle = it) },
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.widget_display_name),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                DisplayNameField(
                    value = config.displayName.orEmpty(),
                    onValueChange = { config = config.copy(displayName = it.take(40).ifBlank { null }) },
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.image_rotation),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RotationButton(
                            icon = Icons.AutoMirrored.Filled.RotateLeft,
                            contentDescription = stringResource(R.string.rotate_counterclockwise),
                            onClick = {
                                config = config.copy(rotationDegrees = config.rotationDegrees - 90)
                            },
                        )
                        Text(
                            text = stringResource(
                                R.string.rotation_degrees,
                                normalizeRotation(config.rotationDegrees),
                            ),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.width(48.dp),
                            textAlign = TextAlign.Center,
                        )
                        RotationButton(
                            icon = Icons.AutoMirrored.Filled.RotateRight,
                            contentDescription = stringResource(R.string.rotate_clockwise),
                            onClick = {
                                config = config.copy(rotationDegrees = config.rotationDegrees + 90)
                            },
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = stringResource(R.string.image_alignment),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    ImageAlignmentSelector(
                        selected = config.imageAlignment,
                        onSelected = { config = config.copy(imageAlignment = it) },
                    )
                }
            }

            Column {
                SettingsCard(verticalPadding = 6.dp) {
                    SettingsCardRow(
                        icon = Icons.Filled.Category,
                        title = stringResource(R.string.shape),
                        subtitle = shapeLabel(config.shape),
                        trailing = {
                            ShapePill(
                                selected = config.shape,
                                onSelect = { config = config.copy(shape = it) },
                            )
                        },
                    )
                    SettingsCardDivider()
                    SettingsCardRow(
                        icon = Icons.Filled.Crop,
                        title = stringResource(R.string.scale_mode),
                        subtitle = scaleLabel(config.scaleMode),
                        trailing = {
                            MiniSegmentedPill(
                                options = listOf(
                                    stringResource(R.string.cover),
                                    stringResource(R.string.contain),
                                ),
                                selectedIndex = ScaleMode.entries.indexOf(config.scaleMode),
                                onSelect = { config = config.copy(scaleMode = ScaleMode.entries[it]) },
                            )
                        },
                    )
                    if (config.shape == WidgetShape.ROUNDED_RECT) {
                        SettingsCardDivider()
                        Column(modifier = Modifier.padding(bottom = 13.dp)) {
                            SettingsCardRow(
                                icon = Icons.Filled.RoundedCorner,
                                title = stringResource(R.string.corner_rounding),
                                subtitle = cornerRoundingLabel(config.cornerRadiusDp),
                                trailing = {
                                    Text(
                                        text = "${config.cornerRadiusDp}dp",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                    )
                                },
                            )
                            Slider(
                                value = config.cornerRadiusDp.toFloat(),
                                onValueChange = { config = config.copy(cornerRadiusDp = it.toInt()) },
                                valueRange = 0f..48f,
                                steps = 47,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(R.string.widget_system_rounding_note),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp, start = 4.dp),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.widget_click_behavior),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TapActionPill(
                    selected = config.clickAction,
                    onSelected = { config = config.copy(clickAction = it) },
                )
            }

            AdBannerPlaceholder()

            GradientPrimaryButton(
                text = saveButtonLabel,
                onClick = { onSave(config) },
                height = 58.dp,
            )

            if (onCancel != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .clip(RoundedCornerShape(29.dp))
                        .border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(29.dp))
                        .clickable(onClick = onCancel),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ChoosePhotoButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(iconBoxBrush())
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.select_photo),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = choosePhotoTextColor(),
        )
    }
}

@Composable
private fun DisplayNameField(value: String, onValueChange: (String) -> Unit) {
    val textColor = MaterialTheme.colorScheme.onSurface
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (value.isEmpty()) {
            Text(
                text = stringResource(R.string.widget_display_name_hint),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(fontSize = 15.sp, color = textColor),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun RotationButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .shadow(4.dp, CircleShape, clip = false)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun FrameStyleSelector(
    selected: FrameStyle,
    onSelected: (FrameStyle) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = stringResource(R.string.frame_style),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(R.string.frame_style_see_all),
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FrameStyle.entries.forEach { style ->
                FrameSwatch(
                    style = style,
                    active = selected == style,
                    onClick = { onSelected(style) },
                )
            }
        }
    }
}

@Composable
private fun FrameSwatch(style: FrameStyle, active: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Box(modifier = Modifier.size(62.dp)) {
            val swatchShape = RoundedCornerShape(
                when (style) {
                    FrameStyle.CLASSIC -> 12.dp
                    FrameStyle.POLAROID -> 3.dp
                    FrameStyle.MINIMAL, FrameStyle.VINTAGE -> 6.dp
                },
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(swatchShape)
                    .then(
                        if (style == FrameStyle.POLAROID) {
                            Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 12.dp)
                        } else {
                            Modifier
                        },
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(frameSwatchBrush(style)),
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 2.5.dp,
                        color = if (active) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = swatchShape,
                    ),
            )
            if (active) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 6.dp, y = (-6).dp)
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
            fontSize = 11.sp,
            fontWeight = if (active) FontWeight.SemiBold else FontWeight.Normal,
            color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 5.dp),
        )
    }
}

@Composable
private fun cornerRoundingLabel(dp: Int): String = when {
    dp <= 8 -> stringResource(R.string.corner_rounding_soft)
    dp <= 20 -> stringResource(R.string.corner_rounding_medium)
    else -> stringResource(R.string.corner_rounding_strong)
}

@Composable
private fun shapeLabel(shape: WidgetShape): String = when (shape) {
    WidgetShape.RECTANGLE -> stringResource(R.string.shape_rectangle)
    WidgetShape.ROUNDED_RECT -> stringResource(R.string.shape_rounded)
    WidgetShape.CIRCLE -> stringResource(R.string.shape_circle)
}

@Composable
private fun scaleLabel(mode: ScaleMode): String = when (mode) {
    ScaleMode.COVER -> stringResource(R.string.cover)
    ScaleMode.CONTAIN -> stringResource(R.string.contain)
}

private val alignmentIcons: Map<ImageAlignment, ImageVector> = mapOf(
    ImageAlignment.TOP_LEFT to Icons.Filled.NorthWest,
    ImageAlignment.TOP to Icons.Filled.North,
    ImageAlignment.TOP_RIGHT to Icons.Filled.NorthEast,
    ImageAlignment.LEFT to Icons.Filled.West,
    ImageAlignment.CENTER to Icons.Filled.FilterCenterFocus,
    ImageAlignment.RIGHT to Icons.Filled.East,
    ImageAlignment.BOTTOM_LEFT to Icons.Filled.SouthWest,
    ImageAlignment.BOTTOM to Icons.Filled.South,
    ImageAlignment.BOTTOM_RIGHT to Icons.Filled.SouthEast,
)

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
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            rows.forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    row.forEach { alignment ->
                        val isSelected = selected == alignment
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .shadow(2.dp, RoundedCornerShape(12.dp), clip = false)
                                .clip(RoundedCornerShape(12.dp))
                                .then(
                                    if (isSelected) {
                                        Modifier.background(accentGradientBrush())
                                    } else {
                                        Modifier.background(MaterialTheme.colorScheme.surface)
                                    },
                                )
                                .clickable { onSelected(alignment) },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = alignmentIcons.getValue(alignment),
                                contentDescription = null,
                                tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Compact shape-preview pill for the Shape row: three small swatches (sharp / rounded / circle)
 * instead of text options, since Ukrainian shape names are too wide for the settings-card row.
 */
@Composable
private fun ShapePill(
    selected: WidgetShape,
    onSelect: (WidgetShape) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(pillTrackBackgroundColor())
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        WidgetShape.entries.forEach { shape ->
            val isSelected = shape == selected
            val previewShape = when (shape) {
                WidgetShape.RECTANGLE -> RoundedCornerShape(2.dp)
                WidgetShape.ROUNDED_RECT -> RoundedCornerShape(8.dp)
                WidgetShape.CIRCLE -> CircleShape
            }
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .then(if (isSelected) Modifier.background(accentGradientBrush()) else Modifier)
                    .clickable { onSelect(shape) },
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(previewShape)
                        .background(
                            if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                )
            }
        }
    }
}

@Composable
private fun TapActionPill(
    selected: WidgetClickAction,
    onSelected: (WidgetClickAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(pillTrackBackgroundColor())
            .padding(3.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        WidgetClickAction.entries.forEach { action ->
            val isSelected = selected == action
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(13.dp))
                    .then(if (isSelected) Modifier.background(accentGradientBrush()) else Modifier)
                    .clickable { onSelected(action) }
                    .padding(vertical = 9.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = when (action) {
                        WidgetClickAction.DECORATIVE -> stringResource(R.string.click_decorative)
                        WidgetClickAction.OPEN_APP -> stringResource(R.string.click_open_app)
                        WidgetClickAction.OPEN_WIDGET_SETTINGS ->
                            stringResource(R.string.click_open_widget_settings)
                    },
                    fontSize = 12.5.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    minLines = 2,
                )
            }
        }
    }
}

private fun normalizeRotation(degrees: Int): Int {
    return ((degrees % 360) + 360) % 360
}

@Composable
private fun WidgetPreviewBox(
    config: WidgetConfig,
    retryKey: Int,
    loadFailed: Boolean,
    onLoadFailed: () -> Unit,
    onChoosePhoto: () -> Unit,
) {
    val showImage = config.imageUri != null && !loadFailed
    // Mock's empty-state placeholder is a fixed 26dp rounded square regardless of the widget's
    // configured shape; once a photo loads, the preview switches to the real shape/radius.
    val shape = if (showImage) {
        val cornerRadius = when (config.shape) {
            WidgetShape.RECTANGLE -> 4.dp
            WidgetShape.ROUNDED_RECT -> config.cornerRadiusDp.dp
            WidgetShape.CIRCLE -> 999.dp
        }
        RoundedCornerShape(cornerRadius)
    } else {
        RoundedCornerShape(26.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape)
            .then(
                if (showImage) {
                    Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
                } else {
                    Modifier
                        .background(dashedBoxBackgroundBrush())
                        .dashedBorder(width = 2.dp, color = dashedBoxBorderColor(), shape = shape)
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (showImage) {
            WidgetImagePreview(
                imageUri = config.imageUri!!,
                rotationDegrees = config.rotationDegrees,
                imageAlignment = config.imageAlignment,
                scaleMode = config.scaleMode,
                frameStyle = config.frameStyle,
                modifier = Modifier.fillMaxSize(),
                retryKey = retryKey,
                errorContent = { LaunchedEffect(Unit) { onLoadFailed() } },
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
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun PhotoErrorNotice(onRetry: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(errorContainerColor())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                tint = errorAccentColor(),
                modifier = Modifier.size(16.dp),
            )
        }
        Text(
            text = stringResource(R.string.photo_load_error),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(1.5.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                .clickable(onClick = onRetry)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = stringResource(R.string.photo_load_retry),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
