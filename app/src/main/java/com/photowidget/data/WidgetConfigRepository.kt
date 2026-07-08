package com.photowidget.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.core.net.toUri
import com.photowidget.widget.WidgetUriHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "widget_configs")

class WidgetConfigRepository(private val context: Context) {

    private val defaultConfigKey = stringPreferencesKey("default_config")

    fun configFlow(appWidgetId: Int): Flow<WidgetConfig> {
        return context.dataStore.data.map { prefs ->
            prefs.toWidgetConfig(keyFor(appWidgetId))
        }
    }

    suspend fun getConfig(appWidgetId: Int): WidgetConfig {
        return configFlow(appWidgetId).first()
    }

    suspend fun saveConfig(appWidgetId: Int, config: WidgetConfig) {
        context.dataStore.edit { prefs ->
            writeConfig(prefs, keyFor(appWidgetId), config)
        }
    }

    suspend fun getDefaultConfig(): WidgetConfig {
        return context.dataStore.data.map { prefs ->
            prefs.toWidgetConfig(defaultConfigKey)
        }.first()
    }

    suspend fun saveDefaultConfig(config: WidgetConfig) {
        context.dataStore.edit { prefs ->
            writeConfig(prefs, defaultConfigKey, config)
        }
    }

    suspend fun ensureWidgetConfig(appWidgetId: Int) {
        val prefs = context.dataStore.data.first()
        val key = keyFor(appWidgetId)
        if (!prefs.contains(keyScaleMode(key))) {
            val default = prefs.toWidgetConfig(defaultConfigKey)
            saveConfig(appWidgetId, default)
            default.imageUri?.let { WidgetUriHelper.ensureReadPermission(context, it.toUri()) }
        }
    }

    suspend fun deleteConfig(appWidgetId: Int) {
        context.dataStore.edit { prefs ->
            prefs.remove(keyDisplayName(keyFor(appWidgetId)))
            prefs.remove(keyImageUri(keyFor(appWidgetId)))
            prefs.remove(keyScaleMode(keyFor(appWidgetId)))
            prefs.remove(keyShape(keyFor(appWidgetId)))
            prefs.remove(keyCornerRadius(keyFor(appWidgetId)))
            prefs.remove(keyClickAction(keyFor(appWidgetId)))
        }
    }

    private fun keyFor(appWidgetId: Int) = stringPreferencesKey("widget_config_$appWidgetId")

    private fun keyImageUri(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_image_uri")

    private fun keyDisplayName(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_display_name")

    private fun keyScaleMode(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_scale_mode")

    private fun keyShape(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_shape")

    private fun keyCornerRadius(base: Preferences.Key<String>) =
        intPreferencesKey("${base.name}_corner_radius")

    private fun keyClickAction(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_click_action")

    private fun Preferences.toWidgetConfig(base: Preferences.Key<String>): WidgetConfig {
        return WidgetConfig(
            displayName = this[keyDisplayName(base)],
            imageUri = this[keyImageUri(base)],
            scaleMode = this[keyScaleMode(base)]?.let { name ->
                ScaleMode.entries.firstOrNull { it.name == name }
            } ?: ScaleMode.COVER,
            shape = this[keyShape(base)]?.let { name ->
                WidgetShape.entries.firstOrNull { it.name == name }
            } ?: WidgetShape.ROUNDED_RECT,
            cornerRadiusDp = this[keyCornerRadius(base)] ?: 16,
            clickAction = this[keyClickAction(base)]?.let { name ->
                WidgetClickAction.entries.firstOrNull { it.name == name }
            } ?: WidgetClickAction.DECORATIVE,
        )
    }

    private fun writeConfig(
        prefs: androidx.datastore.preferences.core.MutablePreferences,
        base: Preferences.Key<String>,
        config: WidgetConfig,
    ) {
        if (config.displayName != null) {
            prefs[keyDisplayName(base)] = config.displayName
        } else {
            prefs.remove(keyDisplayName(base))
        }
        if (config.imageUri != null) {
            prefs[keyImageUri(base)] = config.imageUri
        } else {
            prefs.remove(keyImageUri(base))
        }
        prefs[keyScaleMode(base)] = config.scaleMode.name
        prefs[keyShape(base)] = config.shape.name
        prefs[keyCornerRadius(base)] = config.cornerRadiusDp
        prefs[keyClickAction(base)] = config.clickAction.name
    }
}
