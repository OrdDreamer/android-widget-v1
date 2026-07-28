package com.photowidget.ui.navigation

object Routes {
    const val Launch = "launch"
    const val HomeEmpty = "home_empty"
    const val HomeList = "home_list"

    const val WidgetIdArg = "widgetId"
    const val WidgetSettings = "widgetSettings/{$WidgetIdArg}"
    fun widgetSettings(widgetId: Int) = "widgetSettings/$widgetId"

    const val AppSettings = "appSettings"
    const val Language = "language"
    const val About = "about"
    const val Privacy = "privacy"
}
