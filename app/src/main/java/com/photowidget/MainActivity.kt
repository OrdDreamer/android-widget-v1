package com.photowidget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.photowidget.data.WidgetConfig
import com.photowidget.ui.WidgetSettingsScreen
import com.photowidget.ui.theme.PhotoWidgetTheme
import com.photowidget.widget.PhotoWidgetReceiver
import com.photowidget.widget.WidgetUpdateHelper
import com.photowidget.widget.WidgetUriHelper
import kotlinx.coroutines.launch

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
                var widgetNames by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }
                var editingWidgetId by remember { mutableIntStateOf(-1) }
                var editingDefault by remember { mutableStateOf(false) }
                var defaultConfig by remember { mutableStateOf(WidgetConfig()) }
                val refreshKey = widgetsRefreshKey.intValue
                val startWidgetId = intent?.getIntExtra(EXTRA_EDIT_WIDGET_ID, -1) ?: -1

                LaunchedEffect(startWidgetId) {
                    if (startWidgetId != -1) {
                        editingDefault = false
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
                    widgetNames = widgetIds.associateWith { widgetId ->
                        repository.getConfig(widgetId).displayName?.trim().orEmpty()
                    }.filterValues { it.isNotEmpty() }
                    defaultConfig = repository.getDefaultConfig()
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

                        editingDefault -> {
                            WidgetSettingsScreen(
                                initialConfig = defaultConfig,
                                onSave = { saved ->
                                    scope.launch {
                                        repository.saveDefaultConfig(saved)
                                        defaultConfig = saved
                                        editingDefault = false
                                        Toast.makeText(
                                            this@MainActivity,
                                            R.string.save,
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    }
                                },
                                onCancel = { editingDefault = false },
                            )
                        }

                        else -> {
                            MainScreen(
                                modifier = Modifier.padding(padding),
                                widgetIds = widgetIds,
                                widgetNames = widgetNames,
                                onEditWidget = { editingWidgetId = it },
                                onEditDefault = { editingDefault = true },
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
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    widgetIds: IntArray,
    widgetNames: Map<Int, String>,
    onEditWidget: (Int) -> Unit,
    onEditDefault: () -> Unit,
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

        OutlinedButton(
            onClick = onEditDefault,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.default_settings))
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
                OutlinedButton(
                    onClick = { onEditWidget(widgetId) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val title = widgetNames[widgetId] ?: stringResource(R.string.widget_number, widgetId)
                    Text(title)
                }
            }
        }
    }
}
