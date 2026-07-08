package com.photowidget.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import com.photowidget.PhotoWidgetApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoWidgetReceiver : android.appwidget.AppWidgetProvider() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val tag = "PhotoWidgetReceiver"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        val pendingResult = goAsync()
        scope.launch {
            try {
                appWidgetIds.forEach { appWidgetId ->
                    updateWidgetOnMain(context, appWidgetManager, appWidgetId)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: android.os.Bundle,
    ) {
        Log.d(tag, "onAppWidgetOptionsChanged widgetId=$appWidgetId")
        val pendingResult = goAsync()
        scope.launch {
            try {
                updateWidgetOnMain(context, appWidgetManager, appWidgetId, newOptions)
            } finally {
                pendingResult.finish()
            }
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        val repository = (context.applicationContext as PhotoWidgetApp).widgetConfigRepository
        scope.launch {
            appWidgetIds.forEach { id ->
                repository.deleteConfig(id)
            }
        }
        sendWidgetsChangedBroadcast(context)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        sendWidgetsChangedBroadcast(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        sendWidgetsChangedBroadcast(context)
    }

    private suspend fun updateWidgetOnMain(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        widgetOptions: android.os.Bundle? = null,
    ) {
        val views = withContext(Dispatchers.Default) {
            PhotoWidgetRenderer.buildViews(context, appWidgetManager, appWidgetId, widgetOptions)
        }
        withContext(Dispatchers.Main) {
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun sendWidgetsChangedBroadcast(context: Context) {
        context.sendBroadcast(
            Intent(ACTION_WIDGETS_CHANGED).setPackage(context.packageName),
        )
    }

    companion object {
        const val ACTION_WIDGETS_CHANGED = "com.photowidget.ACTION_WIDGETS_CHANGED"
    }
}
