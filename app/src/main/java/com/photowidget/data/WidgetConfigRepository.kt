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
    private val nextWidgetNumberKey = intPreferencesKey("next_widget_number")

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
            val raw = prefs.toWidgetConfig(defaultConfigKey)
            if (raw.clickAction == WidgetClickAction.DECORATIVE) {
                raw.copy(clickAction = WidgetClickAction.OPEN_WIDGET_SETTINGS)
            } else {
                raw
            }
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
            val rawDefault = prefs.toWidgetConfig(defaultConfigKey)
            val default = if (rawDefault.clickAction == WidgetClickAction.DECORATIVE) {
                rawDefault.copy(clickAction = WidgetClickAction.OPEN_WIDGET_SETTINGS)
            } else {
                rawDefault
            }
            val nextNumber = prefs[nextWidgetNumberKey] ?: 1
            val initial = default.copy(widgetNumber = nextNumber, displayName = null)
            context.dataStore.edit { mutablePrefs ->
                writeConfig(mutablePrefs, key, initial)
                mutablePrefs[nextWidgetNumberKey] = nextNumber + 1
            }
            initial.imageUri?.let { WidgetUriHelper.ensureReadPermission(context, it.toUri()) }
        } else if (!prefs.contains(keyWidgetNumber(key))) {
            val nextNumber = prefs[nextWidgetNumberKey] ?: 1
            context.dataStore.edit { mutablePrefs ->
                mutablePrefs[keyWidgetNumber(key)] = nextNumber
                mutablePrefs[nextWidgetNumberKey] = nextNumber + 1
            }
        }
    }

    suspend fun resetConfig(appWidgetId: Int) {
        val prefs = context.dataStore.data.first()
        val key = keyFor(appWidgetId)
        if (!prefs.contains(keyScaleMode(key))) return

        val existing = prefs.toWidgetConfig(key)
        val default = prefs.toWidgetConfig(defaultConfigKey)
        val fallbackNumber = prefs[nextWidgetNumberKey] ?: 1
        val widgetNumber = existing.widgetNumber.takeIf { it > 0 } ?: fallbackNumber
        val reset = default.copy(
            widgetNumber = widgetNumber,
            displayName = null,
            imageUri = null,
        )
        context.dataStore.edit { mutablePrefs ->
            writeConfig(mutablePrefs, key, reset)
            if (existing.widgetNumber <= 0) {
                mutablePrefs[nextWidgetNumberKey] = widgetNumber + 1
            }
        }
    }

    suspend fun deleteConfig(appWidgetId: Int) {
        context.dataStore.edit { prefs ->
            prefs.remove(keyWidgetNumber(keyFor(appWidgetId)))
            prefs.remove(keyDisplayName(keyFor(appWidgetId)))
            prefs.remove(keyImageUri(keyFor(appWidgetId)))
            prefs.remove(keyScaleMode(keyFor(appWidgetId)))
            prefs.remove(keyShape(keyFor(appWidgetId)))
            prefs.remove(keyCornerRadius(keyFor(appWidgetId)))
            prefs.remove(keyClickAction(keyFor(appWidgetId)))
            prefs.remove(keyRotationDegrees(keyFor(appWidgetId)))
            prefs.remove(keyImageAlignment(keyFor(appWidgetId)))
            prefs.remove(keyFrameStyle(keyFor(appWidgetId)))
        }
    }

    private fun keyFor(appWidgetId: Int) = stringPreferencesKey("widget_config_$appWidgetId")

    private fun keyImageUri(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_image_uri")

    private fun keyDisplayName(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_display_name")

    private fun keyWidgetNumber(base: Preferences.Key<String>) =
        intPreferencesKey("${base.name}_widget_number")

    private fun keyScaleMode(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_scale_mode")

    private fun keyShape(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_shape")

    private fun keyCornerRadius(base: Preferences.Key<String>) =
        intPreferencesKey("${base.name}_corner_radius")

    private fun keyClickAction(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_click_action")

    private fun keyRotationDegrees(base: Preferences.Key<String>) =
        intPreferencesKey("${base.name}_rotation_degrees")

    private fun keyImageAlignment(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_image_alignment")

    private fun keyFrameStyle(base: Preferences.Key<String>) =
        stringPreferencesKey("${base.name}_frame_style")

    private fun Preferences.toWidgetConfig(base: Preferences.Key<String>): WidgetConfig {
        return WidgetConfig(
            widgetNumber = this[keyWidgetNumber(base)] ?: 0,
            displayName = this[keyDisplayName(base)],
            imageUri = this[keyImageUri(base)],
            rotationDegrees = this[keyRotationDegrees(base)] ?: 0,
            imageAlignment = this[keyImageAlignment(base)]?.let { name ->
                ImageAlignment.entries.firstOrNull { it.name == name }
            } ?: ImageAlignment.CENTER,
            scaleMode = this[keyScaleMode(base)]?.let { name ->
                ScaleMode.entries.firstOrNull { it.name == name }
            } ?: ScaleMode.COVER,
            shape = this[keyShape(base)]?.let { name ->
                WidgetShape.entries.firstOrNull { it.name == name }
            } ?: WidgetShape.ROUNDED_RECT,
            cornerRadiusDp = this[keyCornerRadius(base)] ?: 16,
            frameStyle = this[keyFrameStyle(base)]?.let { name ->
                FrameStyle.entries.firstOrNull { it.name == name }
            } ?: FrameStyle.POLAROID,
            clickAction = this[keyClickAction(base)]?.let { name ->
                WidgetClickAction.entries.firstOrNull { it.name == name }
            } ?: WidgetClickAction.OPEN_WIDGET_SETTINGS,
        )
    }

    private fun writeConfig(
        prefs: androidx.datastore.preferences.core.MutablePreferences,
        base: Preferences.Key<String>,
        config: WidgetConfig,
    ) {
        prefs[keyWidgetNumber(base)] = config.widgetNumber
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
        prefs[keyRotationDegrees(base)] = config.rotationDegrees
        prefs[keyImageAlignment(base)] = config.imageAlignment.name
        prefs[keyScaleMode(base)] = config.scaleMode.name
        prefs[keyShape(base)] = config.shape.name
        prefs[keyCornerRadius(base)] = config.cornerRadiusDp
        prefs[keyFrameStyle(base)] = config.frameStyle.name
        prefs[keyClickAction(base)] = config.clickAction.name
    }
}
