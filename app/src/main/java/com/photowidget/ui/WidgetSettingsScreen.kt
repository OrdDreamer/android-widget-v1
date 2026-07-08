package com.photowidget.ui

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.photowidget.R
import com.photowidget.data.WidgetClickAction
import com.photowidget.data.ScaleMode
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape

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
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.preview),
            style = MaterialTheme.typography.titleMedium,
        )

        WidgetPreview(config = config)

        OutlinedButton(
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.select_photo))
        }
        OutlinedTextField(
            value = config.displayName.orEmpty(),
            onValueChange = { config = config.copy(displayName = it.take(40).ifBlank { null }) },
            label = { Text(stringResource(R.string.widget_display_name)) },
            placeholder = { Text(stringResource(R.string.widget_display_name_hint)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = stringResource(R.string.scale_mode),
            style = MaterialTheme.typography.titleMedium,
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
            style = MaterialTheme.typography.titleMedium,
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
            Text(
                text = "${stringResource(R.string.corner_radius)}: ${config.cornerRadiusDp}dp",
                style = MaterialTheme.typography.titleMedium,
            )
            Slider(
                value = config.cornerRadiusDp.toFloat(),
                onValueChange = { config = config.copy(cornerRadiusDp = it.toInt()) },
                valueRange = 0f..48f,
                steps = 47,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            tonalElevation = 1.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.widget_info_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                Text(
                    text = stringResource(R.string.widget_system_rounding_note),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }

        Text(
            text = stringResource(R.string.widget_click_behavior),
            style = MaterialTheme.typography.titleMedium,
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
                            WidgetClickAction.DECORATIVE -> stringResource(R.string.click_decorative)
                            WidgetClickAction.OPEN_APP -> stringResource(R.string.click_open_app)
                            WidgetClickAction.OPEN_WIDGET_SETTINGS -> stringResource(R.string.click_open_widget_settings)
                        },
                        textAlign = TextAlign.Center,
                        minLines = 2,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onSave(config) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(saveButtonLabel)
        }

        if (onCancel != null) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    }
}

@Composable
private fun WidgetPreview(config: WidgetConfig) {
    val shape = when (config.shape) {
        WidgetShape.RECTANGLE -> RoundedCornerShape(0.dp)
        WidgetShape.ROUNDED_RECT -> RoundedCornerShape(config.cornerRadiusDp.dp)
        WidgetShape.CIRCLE -> CircleShape
    }

    val contentScale = when (config.scaleMode) {
        ScaleMode.COVER -> ContentScale.Crop
        ScaleMode.CONTAIN -> ContentScale.Fit
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape)
            .background(Color(0xFFE0E0E0)),
        contentAlignment = Alignment.Center,
    ) {
        if (config.imageUri != null) {
            AsyncImage(
                model = config.imageUri,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = contentScale,
            )
        } else {
            Text(
                text = stringResource(R.string.no_photo_selected),
                color = Color.DarkGray,
            )
        }
    }
}
