package com.photowidget

import android.app.Application
import com.photowidget.data.WidgetConfigRepository

class PhotoWidgetApp : Application() {
    lateinit var widgetConfigRepository: WidgetConfigRepository
        private set

    override fun onCreate() {
        super.onCreate()
        widgetConfigRepository = WidgetConfigRepository(this)
    }
}
