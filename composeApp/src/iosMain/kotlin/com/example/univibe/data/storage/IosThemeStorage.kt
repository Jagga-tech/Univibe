package com.example.univibe.data.storage

import com.example.univibe.domain.models.AppTheme
import platform.Foundation.NSUserDefaults

/**
 * iOS-specific implementation of theme storage using NSUserDefaults
 */
class IosThemeStorage : IThemeStorage {
    
    companion object {
        private const val CURRENT_THEME_KEY = "current_theme"
        private const val CUSTOM_THEME_KEY = "custom_theme"
    }

    private val userDefaults = NSUserDefaults.standardUserDefaults

    override suspend fun saveTheme(theme: AppTheme) {
        val serialized = ThemeSerializer.serialize(theme)
        userDefaults.setObject(serialized, CURRENT_THEME_KEY)
    }

    override suspend fun loadTheme(): AppTheme? {
        return try {
            val serialized = userDefaults.stringForKey(CURRENT_THEME_KEY) ?: return null
            ThemeSerializer.deserialize(serialized)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveCustomTheme(theme: AppTheme) {
        val serialized = ThemeSerializer.serialize(theme)
        userDefaults.setObject(serialized, CUSTOM_THEME_KEY)
    }

    override suspend fun loadCustomTheme(): AppTheme? {
        return try {
            val serialized = userDefaults.stringForKey(CUSTOM_THEME_KEY) ?: return null
            ThemeSerializer.deserialize(serialized)
        } catch (e: Exception) {
            null
        }
    }
}