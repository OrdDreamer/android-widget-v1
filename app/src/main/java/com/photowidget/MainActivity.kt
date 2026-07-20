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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.photowidget.data.WidgetConfig
import com.photowidget.ui.AppSettingsScreen
import com.photowidget.ui.MainScreen
import com.photowidget.ui.WidgetListItem
import com.photowidget.ui.WidgetSettingsScreen
import com.photowidget.ui.enablePhotoWidgetEdgeToEdge
import com.photowidget.ui.theme.PhotoWidgetTheme
import com.photowidget.widget.PhotoWidgetReceiver
import com.photowidget.widget.WidgetUpdateHelper
import com.photowidget.widget.WidgetUriHelper
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private val widgetsRefreshKey = mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        enablePhotoWidgetEdgeToEdge()
        super.onCreate(savedInstanceState)

        val repository = (application as PhotoWidgetApp).widgetConfigRepository

        setContent {
            PhotoWidgetTheme {
                val scope = rememberCoroutineScope()
                var widgetIds by remember { mutableStateOf(intArrayOf()) }
                var widgetItems by remember { mutableStateOf<Map<Int, WidgetListItem>>(emptyMap()) }
                var editingWidgetId by remember { mutableIntStateOf(-1) }
                var deletingWidgetId by remember { mutableIntStateOf(-1) }
                var showingAppSettings by remember { mutableStateOf(false) }
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
                        val title = config.displayName?.trim()
                            ?.takeIf { it.isNotEmpty() }
                            ?: getString(R.string.widget_number, config.widgetNumber)
                        WidgetListItem(
                            title = title,
                            sizeLabel = widgetSizeInCells(manager, widgetId),
                            config = config,
                        )
                    }
                }

                when {
                    showingAppSettings -> {
                        AppSettingsScreen(onBack = { showingAppSettings = false })
                    }

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
                        // Header lives inside MainScreen (Vibrant mockup).
                        MainScreen(
                            modifier = Modifier.fillMaxSize(),
                            widgetIds = widgetIds,
                            widgetItems = widgetItems,
                            onEditWidget = { editingWidgetId = it },
                            onDeleteWidget = { deletingWidgetId = it },
                            onPinWidget = { pinWidget() },
                            onOpenSettings = { showingAppSettings = true },
                        )
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
                                        WidgetUpdateHelper.requestSystemUpdate(
                                            this@MainActivity,
                                            widgetId,
                                        )
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

    private fun pinWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            appWidgetManager.isRequestPinAppWidgetSupported
        ) {
            val pinned = appWidgetManager.requestPinAppWidget(
                ComponentName(this, PhotoWidgetReceiver::class.java),
                null,
                null,
            )
            if (!pinned) {
                Toast.makeText(this, R.string.pin_not_supported, Toast.LENGTH_LONG).show()
            } else {
                widgetsRefreshKey.intValue++
            }
        } else {
            Toast.makeText(this, R.string.pin_not_supported, Toast.LENGTH_LONG).show()
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
        return "${spanX}×${spanY}"
    }

    private fun spanFromDp(sizeDp: Int): Int {
        return (sizeDp / 100f).roundToInt().coerceAtLeast(1)
    }
}
