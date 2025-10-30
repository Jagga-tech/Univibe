package com.example.univibe.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing general app settings.
 */
data class GeneralSettings(
    val darkModeEnabled: Boolean = false,
    val selectedLanguage: String = "English",
    val compactViewEnabled: Boolean = false,
    val autoPlayVideos: Boolean = true
)

/**
 * Data class representing notification settings.
 */
data class NotificationSettings(
    val postsNotifications: Boolean = true,
    val messagesNotifications: Boolean = true,
    val commentsNotifications: Boolean = true,
    val likesNotifications: Boolean = true,
    val groupsNotifications: Boolean = true,
    val eventsNotifications: Boolean = true,
    val pushNotificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = false
)

/**
 * Data class representing privacy settings.
 */
data class PrivacySettings(
    val profileVisibility: String = "Public", // Public, Friends Only, Private
    val showActivityStatus: Boolean = true,
    val allowMessagesFromAnyone: Boolean = true,
    val showOnlineStatus: Boolean = true
)

/**
 * Data class representing account settings.
 */
data class AccountSettings(
    val email: String = "user@example.com",
    val twoFactorAuthEnabled: Boolean = false,
    val lastPasswordChange: String = "30 days ago"
)

/**
 * Settings/Preferences screen - comprehensive settings management.
 *
 * @param generalSettings Initial general settings
 * @param notificationSettings Initial notification settings
 * @param privacySettings Initial privacy settings
 * @param accountSettings Initial account settings
 * @param onBackClick Callback when back button is clicked
 * @param onGeneralSettingsChange Callback when general settings change
 * @param onNotificationSettingsChange Callback when notification settings change
 * @param onPrivacySettingsChange Callback when privacy settings change
 * @param onEditProfileClick Callback when edit profile is clicked
 * @param onChangePasswordClick Callback when change password is clicked
 * @param onLogoutClick Callback when logout is clicked
 * @param onDeleteAccountClick Callback when delete account is clicked
 */
@Composable
fun SettingsScreen(
    generalSettings: GeneralSettings = GeneralSettings(),
    notificationSettings: NotificationSettings = NotificationSettings(),
    privacySettings: PrivacySettings = PrivacySettings(),
    accountSettings: AccountSettings = AccountSettings(),
    onBackClick: () -> Unit = {},
    onGeneralSettingsChange: (GeneralSettings) -> Unit = {},
    onNotificationSettingsChange: (NotificationSettings) -> Unit = {},
    onPrivacySettingsChange: (PrivacySettings) -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onDeleteAccountClick: () -> Unit = {}
) {
    var general by remember { mutableStateOf(generalSettings) }
    var notifications by remember { mutableStateOf(notificationSettings) }
    var privacy by remember { mutableStateOf(privacySettings) }

    var expandedSections by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        SettingsHeader(onBackClick = onBackClick)

        // Settings list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md)
        ) {
            // General Settings Section
            item {
                SettingsSectionHeader(
                    title = "General",
                    isExpanded = expandedSections.contains("general"),
                    onClick = {
                        expandedSections = if (expandedSections.contains("general"))
                            expandedSections - "general" else expandedSections + "general"
                    }
                )
            }

            if (expandedSections.contains("general")) {
                item {
                    SettingToggleItem(
                        title = "Dark Mode",
                        description = "Use dark theme",
                        isEnabled = general.darkModeEnabled,
                        onToggle = {
                            general = general.copy(darkModeEnabled = it)
                            onGeneralSettingsChange(general)
                        }
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Compact View",
                        description = "Use compact layout",
                        isEnabled = general.compactViewEnabled,
                        onToggle = {
                            general = general.copy(compactViewEnabled = it)
                            onGeneralSettingsChange(general)
                        }
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Auto-play Videos",
                        description = "Automatically play videos in feed",
                        isEnabled = general.autoPlayVideos,
                        onToggle = {
                            general = general.copy(autoPlayVideos = it)
                            onGeneralSettingsChange(general)
                        }
                    )
                }

                item {
                    SettingSelectItem(
                        title = "Language",
                        value = general.selectedLanguage,
                        options = listOf("English", "Spanish", "French", "German", "Chinese", "Japanese")
                    )
                }
            }

            // Notification Settings Section
            item {
                SettingsSectionHeader(
                    title = "Notifications",
                    isExpanded = expandedSections.contains("notifications"),
                    onClick = {
                        expandedSections = if (expandedSections.contains("notifications"))
                            expandedSections - "notifications" else expandedSections + "notifications"
                    }
                )
            }

            if (expandedSections.contains("notifications")) {
                item {
                    SettingToggleItem(
                        title = "Push Notifications",
                        description = "Receive push notifications on your device",
                        isEnabled = notifications.pushNotificationsEnabled,
                        onToggle = {
                            notifications = notifications.copy(pushNotificationsEnabled = it)
                            onNotificationSettingsChange(notifications)
                        }
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Email Notifications",
                        description = "Receive email summaries",
                        isEnabled = notifications.emailNotificationsEnabled,
                        onToggle = {
                            notifications = notifications.copy(emailNotificationsEnabled = it)
                            onNotificationSettingsChange(notifications)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
                    Text(
                        text = "Notification Types",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = Dimensions.Spacing.md)
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Posts",
                        description = "New posts from people you follow",
                        isEnabled = notifications.postsNotifications,
                        onToggle = {
                            notifications = notifications.copy(postsNotifications = it)
                            onNotificationSettingsChange(notifications)
                        },
                        isIndented = true
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Messages",
                        description = "Direct messages from other users",
                        isEnabled = notifications.messagesNotifications,
                        onToggle = {
                            notifications = notifications.copy(messagesNotifications = it)
                            onNotificationSettingsChange(notifications)
                        },
                        isIndented = true
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Comments",
                        description = "Replies to your posts and comments",
                        isEnabled = notifications.commentsNotifications,
                        onToggle = {
                            notifications = notifications.copy(commentsNotifications = it)
                            onNotificationSettingsChange(notifications)
                        },
                        isIndented = true
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Likes",
                        description = "When people like your posts",
                        isEnabled = notifications.likesNotifications,
                        onToggle = {
                            notifications = notifications.copy(likesNotifications = it)
                            onNotificationSettingsChange(notifications)
                        },
                        isIndented = true
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Groups",
                        description = "Activity in groups you joined",
                        isEnabled = notifications.groupsNotifications,
                        onToggle = {
                            notifications = notifications.copy(groupsNotifications = it)
                            onNotificationSettingsChange(notifications)
                        },
                        isIndented = true
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Events",
                        description = "Updates about events you're attending",
                        isEnabled = notifications.eventsNotifications,
                        onToggle = {
                            notifications = notifications.copy(eventsNotifications = it)
                            onNotificationSettingsChange(notifications)
                        },
                        isIndented = true
                    )
                }
            }

            // Privacy Settings Section
            item {
                SettingsSectionHeader(
                    title = "Privacy",
                    isExpanded = expandedSections.contains("privacy"),
                    onClick = {
                        expandedSections = if (expandedSections.contains("privacy"))
                            expandedSections - "privacy" else expandedSections + "privacy"
                    }
                )
            }

            if (expandedSections.contains("privacy")) {
                item {
                    SettingSelectItem(
                        title = "Profile Visibility",
                        value = privacy.profileVisibility,
                        options = listOf("Public", "Friends Only", "Private")
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Show Activity Status",
                        description = "Let others see when you're active",
                        isEnabled = privacy.showActivityStatus,
                        onToggle = {
                            privacy = privacy.copy(showActivityStatus = it)
                            onPrivacySettingsChange(privacy)
                        }
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Show Online Status",
                        description = "Display when you're online",
                        isEnabled = privacy.showOnlineStatus,
                        onToggle = {
                            privacy = privacy.copy(showOnlineStatus = it)
                            onPrivacySettingsChange(privacy)
                        }
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Allow Messages from Anyone",
                        description = "Let anyone message you",
                        isEnabled = privacy.allowMessagesFromAnyone,
                        onToggle = {
                            privacy = privacy.copy(allowMessagesFromAnyone = it)
                            onPrivacySettingsChange(privacy)
                        }
                    )
                }
            }

            // Account Settings Section
            item {
                SettingsSectionHeader(
                    title = "Account",
                    isExpanded = expandedSections.contains("account"),
                    onClick = {
                        expandedSections = if (expandedSections.contains("account"))
                            expandedSections - "account" else expandedSections + "account"
                    }
                )
            }

            if (expandedSections.contains("account")) {
                item {
                    SettingNavigationItem(
                        title = "Edit Profile",
                        description = "Change your profile information",
                        onClick = onEditProfileClick
                    )
                }

                item {
                    SettingNavigationItem(
                        title = "Change Password",
                        description = "Update your password",
                        onClick = onChangePasswordClick
                    )
                }

                item {
                    SettingToggleItem(
                        title = "Two-Factor Authentication",
                        description = "Add an extra layer of security",
                        isEnabled = false, // This would be managed separately
                        onToggle = { }
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Spacing.md)
                    ) {
                        Text(
                            text = "Email: ${accountSettings.email}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Last password changed: ${accountSettings.lastPasswordChange}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = Dimensions.Spacing.xs)
                        )
                    }
                }
            }

            // About Section
            item {
                SettingsSectionHeader(
                    title = "About",
                    isExpanded = expandedSections.contains("about"),
                    onClick = {
                        expandedSections = if (expandedSections.contains("about"))
                            expandedSections - "about" else expandedSections + "about"
                    }
                )
            }

            if (expandedSections.contains("about")) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        InfoRow(label = "Version", value = "1.0.0")
                        InfoRow(label = "Build", value = "Build 42")
                        InfoRow(label = "Last Updated", value = "March 2024")
                    }
                }

                item {
                    SettingNavigationItem(
                        title = "Terms of Service",
                        onClick = { }
                    )
                }

                item {
                    SettingNavigationItem(
                        title = "Privacy Policy",
                        onClick = { }
                    )
                }
            }

            // Logout and Delete Account
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }

            item {
                PrimaryButton(
                    text = "Logout",
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = Dimensions.Spacing.md)
                )
            }

            item {
                PrimaryButton(
                    text = "Delete Account",
                    onClick = onDeleteAccountClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = Dimensions.Spacing.md)
                        .padding(top = Dimensions.Spacing.md),
                    backgroundColor = MaterialTheme.colorScheme.error
                )
            }

            // Bottom padding
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Settings header with title and back button.
 */
@Composable
private fun SettingsHeader(
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Expandable section header.
 */
@Composable
private fun SettingsSectionHeader(
    title: String,
    isExpanded: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = Dimensions.Spacing.md,
                vertical = Dimensions.Spacing.md
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

/**
 * Toggle setting item.
 */
@Composable
private fun SettingToggleItem(
    title: String,
    description: String = "",
    isEnabled: Boolean = false,
    onToggle: (Boolean) -> Unit = {},
    isIndented: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onToggle(!isEnabled) }
            .padding(
                start = if (isIndented) Dimensions.Spacing.lg else Dimensions.Spacing.md,
                end = Dimensions.Spacing.md,
                top = Dimensions.Spacing.md,
                bottom = Dimensions.Spacing.md
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Switch(
            checked = isEnabled,
            onCheckedChange = onToggle,
            modifier = Modifier.padding(start = Dimensions.Spacing.md)
        )
    }
}

/**
 * Navigation setting item (clickable with chevron).
 */
@Composable
private fun SettingNavigationItem(
    title: String,
    description: String = "",
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navigate",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Selection/dropdown setting item.
 */
@Composable
private fun SettingSelectItem(
    title: String,
    value: String = "",
    options: List<String> = emptyList()
) {
    var showDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDropdown = !showDropdown }
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = if (showDropdown) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = "Toggle dropdown",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Dropdown options (simplified - in production would use proper dropdown)
        if (showDropdown) {
            options.forEach { option ->
                DropdownOption(
                    label = option,
                    isSelected = option == value,
                    onClick = {
                        showDropdown = false
                        // In production, would call callback to update selection
                    }
                )
            }
        }
    }
}

/**
 * Dropdown option item.
 */
@Composable
private fun DropdownOption(
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                else MaterialTheme.colorScheme.surface
            )
            .clickable(onClick = onClick)
            .padding(
                start = Dimensions.Spacing.lg,
                end = Dimensions.Spacing.md,
                top = Dimensions.Spacing.sm,
                bottom = Dimensions.Spacing.sm
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Info row helper for displaying key-value pairs.
 */
@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}