package com.photowidget.ui.screens.widgetsettings

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.automirrored.rounded.RotateLeft
import androidx.compose.material.icons.automirrored.rounded.RotateRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.photowidget.R
import com.photowidget.data.FrameStyle
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetClickAction
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.components.controls.FrameStyleOption
import com.photowidget.ui.components.controls.PhotoWidgetAlignmentPad
import com.photowidget.ui.components.controls.PhotoWidgetCharacterCounter
import com.photowidget.ui.components.controls.PhotoWidgetFrameStyleSelector
import com.photowidget.ui.components.controls.PhotoWidgetSegmentedControl
import com.photowidget.ui.components.controls.PhotoWidgetSliderPresets
import com.photowidget.ui.components.forms.SelectOption
import com.photowidget.ui.components.controls.SliderPreset
import com.photowidget.ui.components.core.ButtonSize
import com.photowidget.ui.components.core.ButtonVariant
import com.photowidget.ui.components.core.AdBanner
import com.photowidget.ui.components.core.CardPadding
import com.photowidget.ui.components.core.CardVariant
import com.photowidget.ui.components.core.IconButtonSize
import com.photowidget.ui.components.core.IconButtonVariant
import com.photowidget.ui.components.core.PhotoWidgetButton
import com.photowidget.ui.components.core.PhotoWidgetCard
import com.photowidget.ui.components.core.PhotoWidgetIconButton
import com.photowidget.ui.components.forms.PhotoWidgetInput
import com.photowidget.ui.components.forms.PhotoWidgetSelect
import com.photowidget.ui.components.media.PhotoWidgetPhotoPreview
import com.photowidget.ui.photoWidgetNavigationBarPadding
import com.photowidget.ui.photoWidgetSafeAreaPadding
import com.photowidget.ui.theme.Lora
import com.photowidget.ui.theme.PhotoWidgetTheme
import androidx.compose.ui.tooling.preview.PreviewLightDark

/** "Widget Settings.dc.html". Photo-picker logic preserved verbatim from the pre-rebuild screen. */
@Composable
fun WidgetSettingsScreen(
    initialConfig: WidgetConfig,
    onSave: (WidgetConfig) -> Unit,
    onCancel: () -> Unit,
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
    val launchPicker = {
        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .photoWidgetSafeAreaPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PhotoWidgetIconButton(
                icon = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = stringResource(R.string.navigate_back),
                onClick = onCancel,
                variant = IconButtonVariant.Ghost,
            )
            Text(
                text = stringResource(R.string.configure_widget),
                style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic, fontFamily = Lora),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.size(40.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .photoWidgetNavigationBarPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PhotoWidgetPhotoPreview(
                imageUri = config.imageUri,
                rotationDegrees = config.rotationDegrees,
                imageAlignment = config.imageAlignment,
                scaleMode = config.scaleMode,
                frameStyle = config.frameStyle,
                onSelectPhoto = launchPicker,
                modifier = Modifier.padding(top = 8.dp),
            )

            PhotoWidgetButton(
                text = stringResource(if (config.imageUri != null) R.string.change_photo else R.string.select_photo),
                onClick = launchPicker,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                icon = Icons.Rounded.AddPhotoAlternate,
            )

            Section(title = stringResource(R.string.image_alignment)) {
                PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Medium, modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PhotoWidgetAlignmentPad(
                            value = config.imageAlignment,
                            onValueChange = { config = config.copy(imageAlignment = it) },
                        )
                    }
                }
            }

            Section(title = stringResource(R.string.image_rotation)) {
                PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Medium, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        PhotoWidgetIconButton(
                            icon = Icons.AutoMirrored.Rounded.RotateLeft,
                            contentDescription = stringResource(R.string.rotate_counterclockwise),
                            onClick = { config = config.copy(rotationDegrees = config.rotationDegrees - 90) },
                            variant = IconButtonVariant.Filled,
                            size = IconButtonSize.Medium,
                        )
                        Text(
                            text = stringResource(R.string.rotation_degrees, normalizeRotation(config.rotationDegrees)),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.size(width = 48.dp, height = 24.dp),
                        )
                        PhotoWidgetIconButton(
                            icon = Icons.AutoMirrored.Rounded.RotateRight,
                            contentDescription = stringResource(R.string.rotate_clockwise),
                            onClick = { config = config.copy(rotationDegrees = config.rotationDegrees + 90) },
                            variant = IconButtonVariant.Filled,
                            size = IconButtonSize.Medium,
                        )
                    }
                }
            }

            Section(title = stringResource(R.string.scale_mode)) {
                PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Medium, modifier = Modifier.fillMaxWidth()) {
                    PhotoWidgetSegmentedControl(
                        options = listOf(
                            SelectOption(ScaleMode.COVER, stringResource(R.string.cover)),
                            SelectOption(ScaleMode.CONTAIN, stringResource(R.string.contain)),
                        ),
                        value = config.scaleMode,
                        onValueChange = { config = config.copy(scaleMode = it) },
                    )
                }
            }

            Section(title = stringResource(R.string.frame_style)) {
                PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Medium, modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            PhotoWidgetFrameStyleSelector(
                                value = config.toFrameStyleOption(),
                                onValueChange = { config = config.applyFrameStyleOption(it) },
                            )
                        }
                        if (config.shape == WidgetShape.ROUNDED_RECT && config.frameStyle == FrameStyle.CLASSIC) {
                            PhotoWidgetSliderPresets(
                                value = config.cornerRadiusDp,
                                onValueChange = { config = config.copy(cornerRadiusDp = it) },
                                presets = listOf(
                                    SliderPreset(8, stringResource(R.string.corner_rounding_soft)),
                                    SliderPreset(20, stringResource(R.string.corner_rounding_medium)),
                                    SliderPreset(40, stringResource(R.string.corner_rounding_strong)),
                                ),
                                label = stringResource(R.string.corner_radius),
                                valueRange = 0..48,
                            )
                        }
                    }
                }
            }

            Section(title = stringResource(R.string.widget_display_name)) {
                PhotoWidgetInput(
                    value = config.displayName.orEmpty(),
                    onValueChange = { config = config.copy(displayName = it.take(40).ifBlank { null }) },
                    placeholder = stringResource(R.string.widget_display_name_hint),
                    modifier = Modifier.fillMaxWidth(),
                )
                PhotoWidgetCharacterCounter(
                    current = config.displayName.orEmpty().length,
                    max = 40,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                )
            }

            Section(title = null) {
                PhotoWidgetCard(variant = CardVariant.Default, padding = CardPadding.Medium, modifier = Modifier.fillMaxWidth()) {
                    PhotoWidgetSelect(
                        label = stringResource(R.string.widget_click_behavior),
                        value = config.clickAction,
                        options = listOf(
                            SelectOption(WidgetClickAction.DECORATIVE, stringResource(R.string.click_decorative)),
                            SelectOption(WidgetClickAction.OPEN_APP, stringResource(R.string.click_open_app)),
                            SelectOption(WidgetClickAction.OPEN_WIDGET_SETTINGS, stringResource(R.string.click_open_widget_settings)),
                        ),
                        onValueChange = { config = config.copy(clickAction = it) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                PhotoWidgetButton(
                    text = stringResource(R.string.cancel),
                    onClick = onCancel,
                    variant = ButtonVariant.Secondary,
                    size = ButtonSize.Large,
                    modifier = Modifier.weight(1f),
                )
                PhotoWidgetButton(
                    text = stringResource(R.string.save),
                    onClick = { onSave(config) },
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Large,
                    modifier = Modifier.weight(1f),
                )
            }

            AdBanner(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
        }
    }
}

@PreviewLightDark
@Composable
private fun WidgetSettingsScreenPreview() {
    PhotoWidgetTheme {
        WidgetSettingsScreen(initialConfig = WidgetConfig(widgetNumber = 1), onSave = {}, onCancel = {})
    }
}

@Composable
private fun Section(title: String?, content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        content()
    }
}

private fun WidgetConfig.toFrameStyleOption(): FrameStyleOption = when {
    frameStyle == FrameStyle.POLAROID -> FrameStyleOption.Polaroid
    shape == WidgetShape.CIRCLE -> FrameStyleOption.Circle
    shape == WidgetShape.ROUNDED_RECT -> FrameStyleOption.Rounded
    else -> FrameStyleOption.None
}

private fun WidgetConfig.applyFrameStyleOption(option: FrameStyleOption): WidgetConfig = when (option) {
    FrameStyleOption.None -> copy(shape = WidgetShape.RECTANGLE, frameStyle = FrameStyle.CLASSIC)
    FrameStyleOption.Rounded -> copy(shape = WidgetShape.ROUNDED_RECT, frameStyle = FrameStyle.CLASSIC)
    FrameStyleOption.Circle -> copy(shape = WidgetShape.CIRCLE, frameStyle = FrameStyle.CLASSIC)
    FrameStyleOption.Polaroid -> copy(shape = WidgetShape.ROUNDED_RECT, frameStyle = FrameStyle.POLAROID)
}

private fun normalizeRotation(degrees: Int): Int = ((degrees % 360) + 360) % 360
