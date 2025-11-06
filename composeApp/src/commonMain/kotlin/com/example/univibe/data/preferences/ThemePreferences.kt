package com.example.univibe.data.preferences

import com.example.univibe.data.storage.IThemeStorage
import com.example.univibe.domain.models.AppTheme
import com.example.univibe.domain.models.ThemePresets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Singleton object for managing theme preferences with persistence
 * Handles both in-memory state and persistent storage
 */
object ThemePreferences {
    private val _currentTheme = MutableStateFlow<AppTheme>(ThemePresets.DEFAULT_LIGHT)
    val currentTheme: StateFlow<AppTheme> = _currentTheme.asStateFlow()
    
    private var customTheme: AppTheme? = null
    private var storage: IThemeStorage? = null
    private val scope = CoroutineScope(Dispatchers.Default)
    
    /**
     * Initialize the theme preferences with a storage backend
     * Should be called once during app startup (from MainActivity/main activity)
     */
    fun initialize(storage: IThemeStorage) {
        this.storage = storage
        scope.launch {
            // Load saved theme from storage
            val savedTheme = storage.loadTheme()
            if (savedTheme != null) {
                _currentTheme.value = savedTheme
            }
            
            // Load custom theme if it exists
            val savedCustomTheme = storage.loadCustomTheme()
            if (savedCustomTheme != null) {
                customTheme = savedCustomTheme
            }
        }
    }
    
    /**
     * Set the current theme and persist it
     */
    fun setTheme(theme: AppTheme) {
        _currentTheme.value = theme
        // Persist to storage if available
        storage?.let { 
            scope.launch { 
                it.saveTheme(theme) 
            } 
        }
    }
    
    /**
     * Get the current theme
     */
    fun getCurrentTheme(): AppTheme = _currentTheme.value
    
    /**
     * Save a custom theme and make it the current theme
     */
    fun saveCustomTheme(theme: AppTheme) {
        customTheme = theme
        _currentTheme.value = theme
        // Persist both the custom theme and set it as current
        storage?.let { 
            scope.launch {
                it.saveCustomTheme(theme)
                it.saveTheme(theme)
            } 
        }
    }
    
    /**
     * Get the saved custom theme
     */
    fun getCustomTheme(): AppTheme? = customTheme
    
    /**
     * Check if a custom theme has been saved
     */
    fun hasCustomTheme(): Boolean = customTheme != null
}