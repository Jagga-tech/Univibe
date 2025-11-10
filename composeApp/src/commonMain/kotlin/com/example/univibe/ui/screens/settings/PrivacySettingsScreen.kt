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

// Privacy Settings Data Class
data class PrivacyPreferences(
    val profileVisibility: String = "Public", // Public, Friends Only, Private
    val showActivityStatus: Boolean = true,
    val allowMessagesFromAnyone: Boolean = true,
    val showOnlineStatus: Boolean = true,
    val showPostsToPublic: Boolean = true,
    val showFollowersList: Boolean = true,
    val showFollowingList: Boolean = true,
    val allowTagsInPhotos: Boolean = true,
    val searchEngineIndexing: Boolean = true
)

object PrivacySettingsScreen : Screen {
    @Composable
    override fun Content() {
        PrivacySettingsContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrivacySettingsContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var preferences by remember { mutableStateOf(PrivacyPreferences()) }
    var visibilityExpanded by remember { mutableStateOf(false) }
    var showSaveSnackbar by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val visibilityOptions = listOf("Public", "Friends Only", "Private")
    
    LaunchedEffect(showSaveSnackbar) {
        if (showSaveSnackbar) {
            snackbarHostState.showSnackbar("Privacy settings saved")
            showSaveSnackbar = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Settings") },
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
            // Profile Visibility Section
            item {
                Text(
                    text = "Profile Visibility",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ExposedDropdownMenuBox(
                            expanded = visibilityExpanded,
                            onExpandedChange = { visibilityExpanded = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            OutlinedTextField(
                                value = preferences.profileVisibility,
                                onValueChange = {},
                                label = { Text("Who can see your profile?") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = visibilityExpanded) },
                                leadingIcon = {
                                    Icon(PlatformIcons.Visibility, contentDescription = null)
                                }
                            )
                            
                            ExposedDropdownMenu(
                                expanded = visibilityExpanded,
                                onDismissRequest = { visibilityExpanded = false }
                            ) {
                                visibilityOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            preferences = preferences.copy(profileVisibility = option)
                                            visibilityExpanded = false
                                            showSaveSnackbar = true
                                        }
                                    )
                                }
                            }
                        }
                        
                        Text(
                            text = when (preferences.profileVisibility) {
                                "Public" -> "Your profile is visible to everyone on UniVibe"
                                "Friends Only" -> "Only your friends can see your profile"
                                else -> "Only you can see your profile"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
            
            item {
                Divider()
            }
            
            // Status & Activity Section
            item {
                Text(
                    text = "Status & Activity",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Show Online Status",
                    description = "Let others see when you're online",
                    icon = PlatformIcons.Info,
                    isEnabled = preferences.showOnlineStatus,
                    onToggle = {
                        preferences = preferences.copy(showOnlineStatus = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Show Activity Status",
                    description = "Display what you're currently doing",
                    icon = PlatformIcons.Timeline,
                    isEnabled = preferences.showActivityStatus,
                    onToggle = {
                        preferences = preferences.copy(showActivityStatus = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Divider()
            }
            
            // Messages Section
            item {
                Text(
                    text = "Messages",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Allow Messages from Anyone",
                    description = "If off, only friends can message you",
                    icon = PlatformIcons.Mail,
                    isEnabled = preferences.allowMessagesFromAnyone,
                    onToggle = {
                        preferences = preferences.copy(allowMessagesFromAnyone = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Divider()
            }
            
            // Content Visibility Section
            item {
                Text(
                    text = "Content Visibility",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Show Posts to Public",
                    description = "Allow non-friends to see your posts",
                    icon = PlatformIcons.Article,
                    isEnabled = preferences.showPostsToPublic,
                    onToggle = {
                        preferences = preferences.copy(showPostsToPublic = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Show Followers List",
                    description = "Allow others to see who follows you",
                    icon = PlatformIcons.Groups,
                    isEnabled = preferences.showFollowersList,
                    onToggle = {
                        preferences = preferences.copy(showFollowersList = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Show Following List",
                    description = "Allow others to see who you follow",
                    icon = PlatformIcons.PersonAdd,
                    isEnabled = preferences.showFollowingList,
                    onToggle = {
                        preferences = preferences.copy(showFollowingList = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Allow Tags in Photos",
                    description = "Allow others to tag you in photos",
                    icon = PlatformIcons.PhotoLibrary,
                    isEnabled = preferences.allowTagsInPhotos,
                    onToggle = {
                        preferences = preferences.copy(allowTagsInPhotos = it)
                        showSaveSnackbar = true
                    }
                )
            }
            
            item {
                Divider()
            }
            
            // Search Section
            item {
                Text(
                    text = "Search & Discovery",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                PrivacyToggleCard(
                    title = "Search Engine Indexing",
                    description = "Allow search engines to index your profile",
                    icon = PlatformIcons.Search,
                    isEnabled = preferences.searchEngineIndexing,
                    onToggle = {
                        preferences = preferences.copy(searchEngineIndexing = it)
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
private fun PrivacyToggleCard(
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
                tint = MaterialTheme.colorScheme.primary
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
