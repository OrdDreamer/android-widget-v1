package com.photowidget.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.photowidget.PhotoWidgetApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WidgetUpdateHelper {

    suspend fun updateWidget(context: Context, appWidgetId: Int) {
        val manager = AppWidgetManager.getInstance(context)
        val views = withContext(Dispatchers.Default) {
            PhotoWidgetRenderer.buildViews(context, manager, appWidgetId)
        }
        withContext(Dispatchers.Main) {
            manager.updateAppWidget(appWidgetId, views)
        }
    }

    suspend fun updateAllWidgets(context: Context) {
        val manager = AppWidgetManager.getInstance(context)
        val component = ComponentName(context, PhotoWidgetReceiver::class.java)
        val appWidgetIds = manager.getAppWidgetIds(component)
        appWidgetIds.forEach { appWidgetId ->
            updateWidget(context, appWidgetId)
        }
    }

    fun requestSystemUpdate(context: Context, appWidgetId: Int) {
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE).apply {
            component = ComponentName(context, PhotoWidgetReceiver::class.java)
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        }
        context.sendBroadcast(intent)
    }
}
