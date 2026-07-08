package com.photowidget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photowidget.data.WidgetConfig
import com.photowidget.data.WidgetShape
import com.photowidget.ui.WidgetImagePreview
import com.photowidget.ui.WidgetSettingsScreen
import com.photowidget.ui.theme.PhotoWidgetTheme
import com.photowidget.widget.PhotoWidgetReceiver
import com.photowidget.widget.WidgetUpdateHelper
import com.photowidget.widget.WidgetUriHelper
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private val widgetsRefreshKey = mutableIntStateOf(0)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (application as PhotoWidgetApp).widgetConfigRepository

        setContent {
            PhotoWidgetTheme {
                val scope = rememberCoroutineScope()
                var widgetIds by remember { mutableStateOf(intArrayOf()) }
                var widgetItems by remember { mutableStateOf<Map<Int, WidgetListItem>>(emptyMap()) }
                var editingWidgetId by remember { mutableIntStateOf(-1) }
                var deletingWidgetId by remember { mutableIntStateOf(-1) }
                val refreshKey = widgetsRefreshKey.intValue
                val startWidgetId = intent?.getIntExtra(EXTRA_EDIT_WIDGET_ID, -1) ?: -1

                LaunchedEffect(startWidgetId) {
                    if (startWidgetId != -1) {
                        editingWidgetId = startWidgetId
                    }
                }

                DisposableEffect(Unit) {
                    val receiver = object : BroadcastReceiver() {
                        override fun onReceive(context: Context?, intent: Intent?) {
                            widgetsRefreshKey.intValue++
                        }
                    }
                    val filter = IntentFilter(PhotoWidgetReceiver.ACTION_WIDGETS_CHANGED)
                    registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
                    onDispose { unregisterReceiver(receiver) }
                }

                LaunchedEffect(refreshKey) {
                    val manager = AppWidgetManager.getInstance(this@MainActivity)
                    widgetIds = manager.getAppWidgetIds(
                        ComponentName(
                            this@MainActivity,
                            PhotoWidgetReceiver::class.java,
                        ),
                    )
                    widgetItems = widgetIds.associateWith { widgetId ->
                        repository.ensureWidgetConfig(widgetId)
                        val config = repository.getConfig(widgetId)
                        val baseTitle = config.displayName?.trim()
                            ?.takeIf { it.isNotEmpty() }
                            ?: getString(R.string.widget_number, config.widgetNumber)
                        val sizeLabel = widgetSizeInCells(manager, widgetId)
                        WidgetListItem(
                            title = "$baseTitle ($sizeLabel)",
                            config = config,
                        )
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(stringResource(R.string.app_name)) })
                    },
                ) { padding ->
                    when {
                        editingWidgetId != -1 -> {
                            var config by remember(editingWidgetId) {
                                mutableStateOf(WidgetConfig())
                            }
                            LaunchedEffect(editingWidgetId) {
                                config = repository.getConfig(editingWidgetId)
                            }
                            WidgetSettingsScreen(
                                initialConfig = config,
                                onSave = { saved ->
                                    scope.launch {
                                        repository.saveConfig(editingWidgetId, saved)
                                        saved.imageUri?.let {
                                            WidgetUriHelper.ensureReadPermission(
                                                this@MainActivity,
                                                android.net.Uri.parse(it),
                                            )
                                        }
                                        WidgetUpdateHelper.updateWidget(
                                            this@MainActivity,
                                            editingWidgetId,
                                        )
                                        WidgetUpdateHelper.requestSystemUpdate(
                                            this@MainActivity,
                                            editingWidgetId,
                                        )
                                        editingWidgetId = -1
                                        widgetsRefreshKey.intValue++
                                    }
                                },
                                onCancel = { editingWidgetId = -1 },
                            )
                        }

                        else -> {
                            MainScreen(
                                modifier = Modifier.padding(padding),
                                widgetIds = widgetIds,
                                widgetItems = widgetItems,
                                onEditWidget = { editingWidgetId = it },
                                onDeleteWidget = { deletingWidgetId = it },
                                onPinWidget = {
                                    val appWidgetManager = AppWidgetManager.getInstance(this@MainActivity)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                                        appWidgetManager.isRequestPinAppWidgetSupported
                                    ) {
                                        val pinned = appWidgetManager.requestPinAppWidget(
                                            ComponentName(
                                                this@MainActivity,
                                                PhotoWidgetReceiver::class.java,
                                            ),
                                            null,
                                            null,
                                        )
                                        if (!pinned) {
                                            Toast.makeText(
                                                this@MainActivity,
                                                R.string.pin_not_supported,
                                                Toast.LENGTH_LONG,
                                            ).show()
                                        } else {
                                            widgetsRefreshKey.intValue++
                                        }
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.pin_not_supported,
                                            Toast.LENGTH_LONG,
                                        ).show()
                                    }
                                },
                            )
                        }
                    }
                }

                if (deletingWidgetId != -1) {
                    AlertDialog(
                        onDismissRequest = { deletingWidgetId = -1 },
                        title = { Text(stringResource(R.string.delete_widget_title)) },
                        text = { Text(stringResource(R.string.delete_widget_message)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val widgetId = deletingWidgetId
                                    deletingWidgetId = -1
                                    scope.launch {
                                        repository.resetConfig(widgetId)
                                        WidgetUpdateHelper.updateWidget(this@MainActivity, widgetId)
                                        WidgetUpdateHelper.requestSystemUpdate(this@MainActivity, widgetId)
                                        widgetsRefreshKey.intValue++
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.delete_widget_done,
                                            Toast.LENGTH_LONG,
                                        ).show()
                                    }
                                },
                            ) {
                                Text(stringResource(R.string.delete_widget_confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { deletingWidgetId = -1 }) {
                                Text(stringResource(R.string.cancel))
                            }
                        },
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        widgetsRefreshKey.intValue++
    }

    companion object {
        const val EXTRA_EDIT_WIDGET_ID = "extra_edit_widget_id"
    }

    private fun widgetSizeInCells(manager: AppWidgetManager, appWidgetId: Int): String {
        val options = manager.getAppWidgetOptions(appWidgetId)
        val minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, 70)
        val minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, 70)
        val maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, minWidth)
        val maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, minHeight)
        val orientation = resources.configuration.orientation
        val widthDp = if (orientation == Configuration.ORIENTATION_LANDSCAPE) maxWidth else minWidth
        val heightDp = if (orientation == Configuration.ORIENTATION_LANDSCAPE) minHeight else maxHeight
        val spanX = spanFromDp(widthDp)
        val spanY = spanFromDp(heightDp)
        return "${spanX}x$spanY"
    }

    private fun spanFromDp(sizeDp: Int): Int {
        return (sizeDp / 100f).roundToInt().coerceAtLeast(1)
    }
}

private data class WidgetListItem(
    val title: String,
    val config: WidgetConfig,
)

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    widgetIds: IntArray,
    widgetItems: Map<Int, WidgetListItem>,
    onEditWidget: (Int) -> Unit,
    onDeleteWidget: (Int) -> Unit,
    onPinWidget: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Button(
            onClick = onPinWidget,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.pin_widget))
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = stringResource(R.string.active_widgets),
            style = MaterialTheme.typography.titleMedium,
        )

        if (widgetIds.isEmpty()) {
            Text(
                text = stringResource(R.string.no_widgets),
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            widgetIds.forEach { widgetId ->
                val item = widgetItems[widgetId]
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WidgetListThumbnail(config = item?.config)
                    OutlinedButton(
                        onClick = { onEditWidget(widgetId) },
                        modifier = Modifier.weight(1f),
                    ) {
                        val title = item?.title ?: stringResource(R.string.widget_number, widgetId)
                        Text(title)
                    }
                    OutlinedButton(
                        onClick = { onDeleteWidget(widgetId) },
                        modifier = Modifier.width(110.dp),
                    ) {
                        Text(stringResource(R.string.delete_widget_short))
                    }
                }
            }
        }
    }
}

@Composable
private fun WidgetListThumbnail(config: WidgetConfig?) {
    val shape = when (config?.shape) {
        WidgetShape.RECTANGLE, null -> RoundedCornerShape(4.dp)
        WidgetShape.ROUNDED_RECT -> RoundedCornerShape((config.cornerRadiusDp / 2).coerceAtLeast(4).dp)
        WidgetShape.CIRCLE -> CircleShape
    }

    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(shape)
            .background(Color(0xFF2A2A2A)),
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
                color = Color.White.copy(alpha = 0.7f),
            )
        }
    }
}
