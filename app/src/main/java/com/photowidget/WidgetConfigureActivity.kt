package com.photowidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.photowidget.data.WidgetConfig
import com.photowidget.ui.WidgetSettingsScreen
import com.photowidget.ui.enablePhotoWidgetEdgeToEdge
import com.photowidget.ui.theme.PhotoWidgetTheme
import com.photowidget.widget.PhotoWidgetReceiver
import com.photowidget.widget.WidgetUpdateHelper
import com.photowidget.widget.WidgetUriHelper
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WidgetConfigureActivity : ComponentActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        enablePhotoWidgetEdgeToEdge()
        super.onCreate(savedInstanceState)

        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID,
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        setResult(RESULT_CANCELED)

        val repository = (application as PhotoWidgetApp).widgetConfigRepository

        runBlocking {
            repository.ensureWidgetConfig(appWidgetId)
        }

        setContent {
            PhotoWidgetTheme {
                val scope = rememberCoroutineScope()
                var initialConfig by remember { mutableStateOf<WidgetConfig?>(null) }

                androidx.compose.runtime.LaunchedEffect(appWidgetId) {
                    initialConfig = repository.getConfig(appWidgetId)
                }

                val config = initialConfig
                if (config != null) {
                    WidgetSettingsScreen(
                        initialConfig = config,
                        onSave = { saved ->
                            scope.launch {
                                repository.saveConfig(appWidgetId, saved)
                                saved.imageUri?.let {
                                    WidgetUriHelper.ensureReadPermission(
                                        this@WidgetConfigureActivity,
                                        android.net.Uri.parse(it),
                                    )
                                }
                                WidgetUpdateHelper.updateWidget(this@WidgetConfigureActivity, appWidgetId)
                                WidgetUpdateHelper.requestSystemUpdate(
                                    this@WidgetConfigureActivity,
                                    appWidgetId,
                                )
                                sendBroadcast(
                                    Intent(PhotoWidgetReceiver.ACTION_WIDGETS_CHANGED)
                                        .setPackage(packageName),
                                )

                                val resultValue = Intent().putExtra(
                                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                                    appWidgetId,
                                )
                                setResult(RESULT_OK, resultValue)
                                finish()
                            }
                        },
                        onCancel = { finish() },
                    )
                }
            }
        }
    }
}
