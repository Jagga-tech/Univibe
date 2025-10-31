package com.example.univibe.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.*
import com.example.univibe.ui.components.profile.SettingsSectionHeader
import com.example.univibe.ui.components.profile.ToggleSettingsItem
import com.example.univibe.ui.components.profile.NavigationSettingsItem
import com.example.univibe.ui.components.profile.TextSettingsItem
import com.example.univibe.ui.theme.Dimensions

/**
 * Comprehensive application settings screen.
 */
object AppSettingsScreen : Screen {
    @Composable
    override fun Content() {
        AppSettingsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var preferences by remember { mutableStateOf(getCurrentUserPreferences()) }
    var notificationSettings by remember { mutableStateOf(getNotificationSettings()) }
    var accountSettings by remember { mutableStateOf(getAccountSettings()) }
    var showThemeSelector by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md)
        ) {
            // Display Settings
            item {
                SettingsSectionHeader("Display")
            }

            item {
                NavigationSettingsItem(
                    title = "Theme",
                    description = "Choose your preferred theme",
                    value = preferences.theme.capitalize(),
                    onClick = { showThemeSelector = true }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Font Size",
                    description = "Adjust text size",
                    value = preferences.fontSize.capitalize(),
                    onClick = { }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Notification Settings
            item {
                SettingsSectionHeader("Notifications")
            }

            item {
                ToggleSettingsItem(
                    title = "Enable Notifications",
                    description = "Receive all app notifications",
                    isChecked = preferences.notificationsEnabled,
                    onToggle = { newValue ->
                        preferences = preferences.copy(notificationsEnabled = newValue)
                        updateUserPreferences(preferences)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Push Notifications",
                    description = "Receive push notifications",
                    isChecked = preferences.pushNotifications,
                    enabled = preferences.notificationsEnabled,
                    onToggle = { newValue ->
                        preferences = preferences.copy(pushNotifications = newValue)
                        updateUserPreferences(preferences)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Email Notifications",
                    description = "Receive email updates",
                    isChecked = preferences.emailNotifications,
                    enabled = preferences.notificationsEnabled,
                    onToggle = { newValue ->
                        preferences = preferences.copy(emailNotifications = newValue)
                        updateUserPreferences(preferences)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Sound",
                    description = "Play notification sounds",
                    isChecked = preferences.soundEnabled,
                    enabled = preferences.notificationsEnabled,
                    onToggle = { newValue ->
                        preferences = preferences.copy(soundEnabled = newValue)
                        updateUserPreferences(preferences)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Vibration",
                    description = "Vibrate on notifications",
                    isChecked = preferences.vibrationEnabled,
                    enabled = preferences.notificationsEnabled,
                    onToggle = { newValue ->
                        preferences = preferences.copy(vibrationEnabled = newValue)
                        updateUserPreferences(preferences)
                    }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Specific Notification Types
            item {
                SettingsSectionHeader("Notification Types")
            }

            item {
                ToggleSettingsItem(
                    title = "Likes & Comments",
                    isChecked = notificationSettings.likeNotifications,
                    onToggle = { newValue ->
                        notificationSettings = notificationSettings.copy(likeNotifications = newValue)
                        updateNotificationSettings(notificationSettings)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Follow Notifications",
                    isChecked = notificationSettings.followNotifications,
                    onToggle = { newValue ->
                        notificationSettings = notificationSettings.copy(followNotifications = newValue)
                        updateNotificationSettings(notificationSettings)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Messages",
                    isChecked = notificationSettings.messageNotifications,
                    onToggle = { newValue ->
                        notificationSettings = notificationSettings.copy(messageNotifications = newValue)
                        updateNotificationSettings(notificationSettings)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Event Updates",
                    isChecked = notificationSettings.eventNotifications,
                    onToggle = { newValue ->
                        notificationSettings = notificationSettings.copy(eventNotifications = newValue)
                        updateNotificationSettings(notificationSettings)
                    }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Privacy & Security
            item {
                SettingsSectionHeader("Privacy & Security")
            }

            item {
                NavigationSettingsItem(
                    title = "Privacy",
                    description = "Control who can see your profile",
                    onClick = { }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Blocked Users",
                    description = "Manage blocked users",
                    value = getBlockedUsers().size.toString(),
                    onClick = { }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Online Status",
                    description = "Show when you're online",
                    isChecked = preferences.showOnlineStatus,
                    onToggle = { newValue ->
                        preferences = preferences.copy(showOnlineStatus = newValue)
                        updateUserPreferences(preferences)
                    }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Two-Factor Authentication",
                    description = "Add an extra layer of security",
                    isChecked = accountSettings.twoFactorEnabled,
                    onToggle = { newValue ->
                        accountSettings = accountSettings.copy(twoFactorEnabled = newValue)
                        toggleTwoFactor(newValue)
                    }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Account
            item {
                SettingsSectionHeader("Account")
            }

            item {
                NavigationSettingsItem(
                    title = "Change Password",
                    description = "Update your password",
                    onClick = { }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Login Sessions",
                    description = "Manage active sessions",
                    value = "${accountSettings.loginSessions.size} active",
                    onClick = { }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Connected Apps",
                    description = "Manage app permissions",
                    value = "${accountSettings.connectedApps.size} connected",
                    onClick = { }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // About & Support
            item {
                SettingsSectionHeader("About & Support")
            }

            item {
                TextSettingsItem(
                    title = "App Version",
                    value = "1.0.0"
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Help & Support",
                    onClick = { }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Terms of Service",
                    onClick = { }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "Privacy Policy",
                    onClick = { }
                )
            }

            item {
                NavigationSettingsItem(
                    title = "About UniVibe",
                    onClick = { }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Danger Zone
            item {
                SettingsSectionHeader("Danger Zone")
            }

            item {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.md),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Logout")
                }
            }

            item {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.md),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete Account")
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Format enum string to readable format.
 */
private fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercase() }
}