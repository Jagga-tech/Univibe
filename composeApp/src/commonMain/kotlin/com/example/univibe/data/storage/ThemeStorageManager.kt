package com.example.univibe.data.storage

import com.example.univibe.domain.models.AppTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Serializable representation of AppTheme for storage
 */
@Serializable
data class StoredTheme(
    val id: String,
    val name: String,
    val primary: Long,
    val secondary: Long,
    val tertiary: Long,
    val background: Long,
    val surface: Long,
    val error: Long,
    val isDark: Boolean,
    val isCustom: Boolean = false
) {
    fun toAppTheme(): AppTheme = AppTheme(
        id = id,
        name = name,
        primary = primary,
        secondary = secondary,
        tertiary = tertiary,
        background = background,
        surface = surface,
        error = error,
        isDark = isDark,
        isCustom = isCustom
    )
}

fun AppTheme.toStoredTheme(): StoredTheme = StoredTheme(
    id = id,
    name = name,
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    background = background,
    surface = surface,
    error = error,
    isDark = isDark,
    isCustom = isCustom
)

/**
 * Platform-agnostic interface for theme storage
 */
interface IThemeStorage {
    suspend fun saveTheme(theme: AppTheme)
    suspend fun loadTheme(): AppTheme?
    suspend fun saveCustomTheme(theme: AppTheme)
    suspend fun loadCustomTheme(): AppTheme?
}

/**
 * JSON serialization handler for themes
 */
object ThemeSerializer {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun serialize(theme: AppTheme): String = json.encodeToString(
        StoredTheme.serializer(),
        theme.toStoredTheme()
    )

    fun deserialize(data: String): AppTheme? = try {
        json.decodeFromString(
            StoredTheme.serializer(),
            data
        ).toAppTheme()
    } catch (e: Exception) {
        null
    }
}