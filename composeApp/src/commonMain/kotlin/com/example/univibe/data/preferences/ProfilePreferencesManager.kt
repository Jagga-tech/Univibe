package com.example.univibe.data.preferences

import com.example.univibe.ui.screens.settings.*

/**
 * Manager for handling all user preferences and settings.
 * Provides a centralized way to access, update, and persist user preferences.
 *
 * This would typically integrate with:
 * - DataStore (for local persistence)
 * - Backend API (for cloud sync)
 * - SharedPreferences (fallback for older systems)
 */
class ProfilePreferencesManager {
    
    // General Settings
    private var generalSettings = GeneralSettings()
    
    // Notification Preferences
    private var notificationPreferences = NotificationPreferences()
    
    // Privacy Preferences
    private var privacyPreferences = PrivacyPreferences()
    
    // Account Info
    private var accountInfo = AccountInfo()
    
    // Getters
    fun getGeneralSettings(): GeneralSettings = generalSettings.copy()
    
    fun getNotificationPreferences(): NotificationPreferences = notificationPreferences.copy()
    
    fun getPrivacyPreferences(): PrivacyPreferences = privacyPreferences.copy()
    
    fun getAccountInfo(): AccountInfo = accountInfo.copy()
    
    // Setters
    fun updateGeneralSettings(settings: GeneralSettings) {
        generalSettings = settings
        // TODO: Persist to backend/local storage
    }
    
    fun updateNotificationPreferences(preferences: NotificationPreferences) {
        notificationPreferences = preferences
        // TODO: Persist to backend/local storage
    }
    
    fun updatePrivacyPreferences(preferences: PrivacyPreferences) {
        privacyPreferences = preferences
        // TODO: Persist to backend/local storage
    }
    
    fun updateAccountInfo(info: AccountInfo) {
        accountInfo = info
        // TODO: Persist to backend/local storage
    }
    
    // Helper methods
    fun resetToDefaults() {
        generalSettings = GeneralSettings()
        notificationPreferences = NotificationPreferences()
        privacyPreferences = PrivacyPreferences()
    }
    
    fun exportSettings(): Map<String, Any> {
        return mapOf(
            "general" to generalSettings,
            "notifications" to notificationPreferences,
            "privacy" to privacyPreferences,
            "account" to accountInfo
        )
    }
    
    companion object {
        // Simple cross-platform singleton pattern (no @Volatile or synchronized)
        private var instance: ProfilePreferencesManager? = null
        
        fun getInstance(): ProfilePreferencesManager {
            return instance ?: ProfilePreferencesManager().also { instance = it }
        }
    }
}
