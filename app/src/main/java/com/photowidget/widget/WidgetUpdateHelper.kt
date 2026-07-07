package com.photowidget.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager

object WidgetUpdateHelper {

    suspend fun updateWidget(context: Context, appWidgetId: Int) {
        val manager = GlanceAppWidgetManager(context)
        val glanceId = manager.getGlanceIdBy(appWidgetId)
        PhotoWidget().update(context, glanceId)
    }

    suspend fun updateAllWidgets(context: Context) {
        val manager = GlanceAppWidgetManager(context)
        val widget = PhotoWidget()
        val component = ComponentName(context, PhotoWidgetReceiver::class.java)
        val appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(component)
        appWidgetIds.forEach { appWidgetId ->
            val glanceId = manager.getGlanceIdBy(appWidgetId)
            widget.update(context, glanceId)
        }
    }
}
