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
import androidx.lifecycle.lifecycleScope
import com.photowidget.data.WidgetConfig
import com.photowidget.ui.WidgetSettingsScreen
import com.photowidget.ui.theme.PhotoWidgetTheme
import com.photowidget.widget.WidgetUpdateHelper
import kotlinx.coroutines.launch

class WidgetConfigureActivity : ComponentActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
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

        lifecycleScope.launch {
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
                                WidgetUpdateHelper.updateWidget(this@WidgetConfigureActivity, appWidgetId)

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
