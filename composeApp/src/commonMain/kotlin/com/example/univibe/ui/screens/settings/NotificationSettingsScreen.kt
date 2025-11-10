package com.example.univibe.ui.screens.settings

import com.example.univibe.ui.theme.PlatformIcons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Notification Settings Data Class
data class NotificationPreferences(
    val postsNotifications: Boolean = true,
    val messagesNotifications: Boolean = true,
    val commentsNotifications: Boolean = true,
    val likesNotifications: Boolean = true,
    val followNotifications: Boolean = true,
    val eventsNotifications: Boolean = true,
    val pushNotificationsEnabled: Boolean = true,
    val emailNotificationsEnabled: Boolean = false,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)

object NotificationSettingsScreen : Screen {
    @Composable
    override fun Content() {
        NotificationSettingsContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationSettingsContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var preferences by remember { mutableStateOf(NotificationPreferences()) }
    var isSaving by remember { mutableStateOf(false) }
    var showSaveSnackbar by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(showSaveSnackbar) {
        if (showSaveSnackbar) {
            snackbarHostState.showSnackbar("Notification settings saved")
            showSaveSnackbar = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Settings") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Push Notifications Section
            item {
                NotificationSettingCard(
                    title = "Push Notifications",
                    description = "Receive push notifications on your device",
                    isEnabled = preferences.pushNotificationsEnabled,
                    onToggle = { 
                        preferences = preferences.copy(pushNotificationsEnabled = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            // Email Notifications Section
            item {
                NotificationSettingCard(
                    title = "Email Notifications",
                    description = "Receive email notifications for important updates",
                    isEnabled = preferences.emailNotificationsEnabled,
                    onToggle = { 
                        preferences = preferences.copy(emailNotificationsEnabled = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Divider()
            }
            
            // Notification Types Section
            item {
                Text(
                    text = "Notification Types",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                NotificationTypeToggle(
                    title = "New Posts",
                    description = "Notify when friends post",
                    icon = PlatformIcons.Article,
                    isEnabled = preferences.postsNotifications,
                    onToggle = { 
                        preferences = preferences.copy(postsNotifications = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                NotificationTypeToggle(
                    title = "Messages",
                    description = "Notify for new messages",
                    icon = PlatformIcons.Mail,
                    isEnabled = preferences.messagesNotifications,
                    onToggle = { 
                        preferences = preferences.copy(messagesNotifications = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                NotificationTypeToggle(
                    title = "Comments",
                    description = "Notify when someone comments on your post",
                    icon = PlatformIcons.Comment,
                    isEnabled = preferences.commentsNotifications,
                    onToggle = { 
                        preferences = preferences.copy(commentsNotifications = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                NotificationTypeToggle(
                    title = "Likes",
                    description = "Notify when someone likes your post",
                    icon = PlatformIcons.Favorite,
                    isEnabled = preferences.likesNotifications,
                    onToggle = { 
                        preferences = preferences.copy(likesNotifications = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                NotificationTypeToggle(
                    title = "New Followers",
                    description = "Notify when someone follows you",
                    icon = PlatformIcons.PersonAdd,
                    isEnabled = preferences.followNotifications,
                    onToggle = { 
                        preferences = preferences.copy(followNotifications = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                NotificationTypeToggle(
                    title = "Events",
                    description = "Notify for event updates and reminders",
                    icon = PlatformIcons.Event,
                    isEnabled = preferences.eventsNotifications,
                    onToggle = { 
                        preferences = preferences.copy(eventsNotifications = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Divider()
            }
            
            // Sound & Vibration Section
            item {
                Text(
                    text = "Sound & Vibration",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                NotificationSettingCard(
                    title = "Sound",
                    description = "Play sound for notifications",
                    isEnabled = preferences.soundEnabled,
                    onToggle = { 
                        preferences = preferences.copy(soundEnabled = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                NotificationSettingCard(
                    title = "Vibration",
                    description = "Vibrate for notifications",
                    isEnabled = preferences.vibrationEnabled,
                    onToggle = { 
                        preferences = preferences.copy(vibrationEnabled = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun NotificationSettingCard(
    title: String,
    description: String,
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
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
private fun NotificationTypeToggle(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 12.dp),
                tint = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
