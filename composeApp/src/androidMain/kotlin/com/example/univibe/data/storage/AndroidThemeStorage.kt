package com.example.univibe.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.univibe.domain.models.AppTheme
import kotlinx.coroutines.flow.firstOrNull

/**
 * Android-specific implementation of theme storage using DataStore
 */
class AndroidThemeStorage(private val context: Context) : IThemeStorage {
    
    companion object {
        private const val THEME_PREFERENCES_NAME = "theme_preferences"
        private val CURRENT_THEME_KEY = stringPreferencesKey("current_theme")
        private val CUSTOM_THEME_KEY = stringPreferencesKey("custom_theme")
    }

    private val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(
        name = THEME_PREFERENCES_NAME
    )

    override suspend fun saveTheme(theme: AppTheme) {
        val serialized = ThemeSerializer.serialize(theme)
        context.themeDataStore.edit { preferences ->
            preferences[CURRENT_THEME_KEY] = serialized
        }
    }

    override suspend fun loadTheme(): AppTheme? {
        return try {
            val preferences = context.themeDataStore.data.firstOrNull() ?: return null
            val serialized = preferences[CURRENT_THEME_KEY] ?: return null
            ThemeSerializer.deserialize(serialized)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCustomTheme(theme: AppTheme) {
        val serialized = ThemeSerializer.serialize(theme)
        context.themeDataStore.edit { preferences ->
            preferences[CUSTOM_THEME_KEY] = serialized
        }
    }

    override suspend fun loadCustomTheme(): AppTheme? {
        return try {
            val preferences = context.themeDataStore.data.firstOrNull() ?: return null
            val serialized = preferences[CUSTOM_THEME_KEY] ?: return null
            ThemeSerializer.deserialize(serialized)
        } catch (e: Exception) {
            null
        }
    }
}