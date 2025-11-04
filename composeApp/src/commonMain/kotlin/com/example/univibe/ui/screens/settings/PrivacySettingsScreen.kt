package com.example.univibe.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.*
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.components.profile.SettingsSectionHeader
import com.example.univibe.ui.components.profile.ToggleSettingsItem
import com.example.univibe.ui.components.profile.NavigationSettingsItem
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Detailed privacy settings screen.
 */
object PrivacySettingsScreen : Screen {
    @Composable
    override fun Content() {
        PrivacySettingsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PrivacySettingsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var profileVisibility by remember { mutableStateOf("public") }
    var showFollowersList by remember { mutableStateOf(true) }
    var showFollowingList by remember { mutableStateOf(true) }
    var showActivityStatus by remember { mutableStateOf(true) }
    var allowSearchByEmail by remember { mutableStateOf(true) }
    var allowSearchByPhone by remember { mutableStateOf(false) }
    var showBirthday by remember { mutableStateOf(false) }

    val blockedUsers = remember { mutableStateOf(getBlockedUsers()) }
    val mutedUsers = remember { mutableStateOf(getMutedUsers()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(UISymbols.BACK, contentDescription = "Back")
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
            // Profile Visibility
            item {
                SettingsSectionHeader("Profile Visibility")
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(Dimensions.Spacing.md)
                ) {
                    listOf("Public", "Friends Only", "Private").forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Dimensions.Spacing.sm),
                            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
                        ) {
                            RadioButton(
                                selected = profileVisibility == option.lowercase(),
                                onClick = { profileVisibility = option.lowercase() }
                            )
                            Text(
                                text = option,
                                modifier = Modifier
                                    .align(androidx.compose.ui.Alignment.CenterVertically)
                                    .weight(1f)
                            )
                        }
                    }
                }
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Who Can See Your Info
            item {
                SettingsSectionHeader("Who Can See Your Info")
            }

            item {
                ToggleSettingsItem(
                    title = "Show Followers List",
                    description = "Allow others to see your followers",
                    isChecked = showFollowersList,
                    onToggle = { showFollowersList = it }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Show Following List",
                    description = "Allow others to see who you follow",
                    isChecked = showFollowingList,
                    onToggle = { showFollowingList = it }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Show Activity Status",
                    description = "Let others see when you're online",
                    isChecked = showActivityStatus,
                    onToggle = { showActivityStatus = it }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Show Birthday",
                    description = "Display your birthday on your profile",
                    isChecked = showBirthday,
                    onToggle = { showBirthday = it }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Discoverability
            item {
                SettingsSectionHeader("Discoverability")
            }

            item {
                ToggleSettingsItem(
                    title = "Searchable by Email",
                    description = "Let people find you by email",
                    isChecked = allowSearchByEmail,
                    onToggle = { allowSearchByEmail = it }
                )
            }

            item {
                ToggleSettingsItem(
                    title = "Searchable by Phone",
                    description = "Let people find you by phone number",
                    isChecked = allowSearchByPhone,
                    onToggle = { allowSearchByPhone = it }
                )
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Blocked Users
            item {
                SettingsSectionHeader("Blocked Users")
            }

            item {
                Text(
                    text = "${blockedUsers.value.size} blocked",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(Dimensions.Spacing.md)
                )
            }

            item {
                if (blockedUsers.value.isNotEmpty()) {
                    blockedUsers.value.forEach { blockedUser ->
                        BlockedUserItem(
                            blockedUserId = blockedUser.blockedUserId,
                            reason = blockedUser.reason,
                            onUnblock = {
                                blockedUsers.value = blockedUsers.value.filter { 
                                    it.blockedUserId != blockedUser.blockedUserId 
                                }
                                removeBlockedUser(blockedUser.blockedUserId)
                            }
                        )
                    }
                } else {
                    Text(
                        text = "No blocked users",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(Dimensions.Spacing.md)
                    )
                }
            }

            item {
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
            }

            // Muted Users
            item {
                SettingsSectionHeader("Muted Users")
            }

            item {
                Text(
                    text = "${mutedUsers.value.size} muted",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(Dimensions.Spacing.md)
                )
            }

            item {
                if (mutedUsers.value.isNotEmpty()) {
                    mutedUsers.value.forEach { mutedUser ->
                        MutedUserItem(
                            mutedUserId = mutedUser.blockedUserId,
                            reason = mutedUser.reason,
                            onUnmute = {
                                mutedUsers.value = mutedUsers.value.filter { 
                                    it.blockedUserId != mutedUser.blockedUserId 
                                }
                            }
                        )
                    }
                } else {
                    Text(
                        text = "No muted users",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(Dimensions.Spacing.md)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Item representing a blocked user.
 */
@Composable
private fun BlockedUserItem(
    blockedUserId: String,
    reason: String? = null,
    onUnblock: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = blockedUserId,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!reason.isNullOrEmpty()) {
                    Text(
                        text = reason,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            TextButton(onClick = onUnblock) {
                Text("Unblock", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

/**
 * Item representing a muted user.
 */
@Composable
private fun MutedUserItem(
    mutedUserId: String,
    reason: String? = null,
    onUnmute: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = mutedUserId,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!reason.isNullOrEmpty()) {
                    Text(
                        text = reason,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            TextButton(onClick = onUnmute) {
                Text("Unmute", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}