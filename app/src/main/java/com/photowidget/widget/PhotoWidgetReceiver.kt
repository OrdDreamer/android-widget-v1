package com.photowidget.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.photowidget.PhotoWidgetApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PhotoWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: PhotoWidget = PhotoWidget()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        val repository = (context.applicationContext as PhotoWidgetApp).widgetConfigRepository
        scope.launch {
            appWidgetIds.forEach { id ->
                repository.deleteConfig(id)
            }
        }
    }
}
