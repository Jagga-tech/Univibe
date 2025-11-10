package com.example.univibe.ui.screens.settings

import com.example.univibe.ui.theme.PlatformIcons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// General App Settings Data Class
data class GeneralSettings(
    val darkModeEnabled: Boolean = false,
    val selectedLanguage: String = "English",
    val compactViewEnabled: Boolean = false,
    val autoPlayVideos: Boolean = true,
    val appVersion: String = "1.0.0",
    val buildNumber: String = "1"
)

object AppSettingsScreen : Screen {
    @Composable
    override fun Content() {
        AppSettingsContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppSettingsContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var generalSettings by remember { mutableStateOf(GeneralSettings()) }
    var expandedSections by remember { mutableStateOf(setOf<String>()) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showSaveSnackbar by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(showSaveSnackbar) {
        if (showSaveSnackbar) {
            snackbarHostState.showSnackbar("Settings saved")
            showSaveSnackbar = false
        }
    }
    
    // Logout Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        scope.launch {
                            delay(1000)
                            // TODO: Navigate to login screen after logout
                        }
                    }
                ) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(PlatformIcons.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Profile Section
            item {
                SettingsSectionHeader(
                    title = "Profile & Account",
                    icon = PlatformIcons.Person
                )
            }
            
            item {
                SettingMenuCard(
                    title = "Edit Profile",
                    description = "Update your profile information",
                    icon = PlatformIcons.Edit,
                    onClick = { /* TODO: Navigate to edit profile */ }
                )
            }
            
            item {
                SettingMenuCard(
                    title = "Account Settings",
                    description = "Manage your account security",
                    icon = PlatformIcons.VpnKey,
                    onClick = { navigator.push(AccountSettingsScreen) }
                )
            }
            
            item {
                Divider()
            }
            
            // Preferences Section
            item {
                SettingsSectionHeader(
                    title = "Preferences",
                    icon = PlatformIcons.Settings
                )
            }
            
            item {
                SettingToggleCard(
                    title = "Dark Mode",
                    description = "Use dark theme",
                    icon = PlatformIcons.DarkMode,
                    isEnabled = generalSettings.darkModeEnabled,
                    onToggle = {
                        generalSettings = generalSettings.copy(darkModeEnabled = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                SettingToggleCard(
                    title = "Compact View",
                    description = "Use compact layout for lists",
                    icon = PlatformIcons.ViewCompact,
                    isEnabled = generalSettings.compactViewEnabled,
                    onToggle = {
                        generalSettings = generalSettings.copy(compactViewEnabled = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                SettingToggleCard(
                    title = "Auto-play Videos",
                    description = "Automatically play videos in feed",
                    icon = PlatformIcons.Videocam,
                    isEnabled = generalSettings.autoPlayVideos,
                    onToggle = {
                        generalSettings = generalSettings.copy(autoPlayVideos = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Divider()
            }
            
            // Notifications & Privacy Section
            item {
                SettingsSectionHeader(
                    title = "Notifications & Privacy",
                    icon = PlatformIcons.Security
                )
            }
            
            item {
                SettingMenuCard(
                    title = "Notification Settings",
                    description = "Manage notification preferences",
                    icon = PlatformIcons.Notifications,
                    onClick = { navigator.push(NotificationSettingsScreen) }
                )
            }
            
            item {
                SettingMenuCard(
                    title = "Privacy Settings",
                    description = "Control who can see your profile",
                    icon = PlatformIcons.PrivacyTip,
                    onClick = { navigator.push(PrivacySettingsScreen) }
                )
            }
            
            item {
                Divider()
            }
            
            // About Section
            item {
                SettingsSectionHeader(
                    title = "About & Support",
                    icon = PlatformIcons.Info
                )
            }
            
            item {
                InfoCard(
                    label = "App Version",
                    value = generalSettings.appVersion
                )
            }
            
            item {
                InfoCard(
                    label = "Build Number",
                    value = generalSettings.buildNumber
                )
            }
            
            item {
                SettingMenuCard(
                    title = "Help & Support",
                    description = "Get help and report issues",
                    icon = PlatformIcons.Help,
                    onClick = { /* TODO: Open help */ }
                )
            }
            
            item {
                SettingMenuCard(
                    title = "About UniVibe",
                    description = "Learn more about the app",
                    icon = PlatformIcons.Info,
                    onClick = { /* TODO: Open about */ }
                )
            }
            
            item {
                Divider()
            }
            
            // Session Section
            item {
                SettingsSectionHeader(
                    title = "Session",
                    icon = PlatformIcons.Logout
                )
            }
            
            item {
                OutlinedButton(
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(PlatformIcons.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout")
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun SettingMenuCard(
    title: String,
    description: String = "",
    icon: androidx.compose.material.icons.materialIcon,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall
                    )
                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
            
            Icon(
                imageVector = PlatformIcons.ChevronRight,
                contentDescription = "Navigate",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingToggleCard(
    title: String,
    description: String = "",
    icon: androidx.compose.material.icons.materialIcon,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(24.dp),
                    tint = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall
                    )
                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
            
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
private fun InfoCard(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
