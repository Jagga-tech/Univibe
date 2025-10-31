package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.getOtherUserProfile
import com.example.univibe.domain.models.UserProfileData
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.OutlineButton
import com.example.univibe.ui.components.profile.ProfileStatsCard
import com.example.univibe.ui.theme.Dimensions

/**
 * Screen for viewing another user's profile (read-only).
 */
data class ViewProfileScreen(val userId: String) : Screen {
    @Composable
    override fun Content() {
        ViewProfileScreenContent(userId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewProfileScreenContent(userId: String) {
    val navigator = LocalNavigator.currentOrThrow
    // In a real app, fetch profile by userId from API
    val profile = remember { mutableStateOf(getOtherUserProfile()) }
    var isFollowing by remember { mutableStateOf(profile.value.isFollowing) }
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(profile.value.fullName) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Block User") },
                            onClick = { showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Report User") },
                            onClick = { showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("View Mutual Connections") },
                            onClick = { showMenu = false }
                        )
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
            contentPadding = PaddingValues(Dimensions.Spacing.md)
        ) {
            // Profile Header
            item {
                OtherUserProfileHeader(profile.value)
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.md),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
                ) {
                    PrimaryButton(
                        text = if (isFollowing) "Following" else "Follow",
                        onClick = {
                            isFollowing = !isFollowing
                        },
                        modifier = Modifier.weight(1f)
                    )

                    OutlineButton(
                        text = "Message",
                        onClick = {
                            // Navigate to chat screen
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Stats
            item {
                Text(
                    text = "Activity",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(Dimensions.Spacing.md)
                )
            }

            item {
                ProfileStatsCard(
                    postsCount = profile.value.postsCount,
                    followersCount = profile.value.followersCount,
                    followingCount = profile.value.followingCount
                )
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Bio Section
            item {
                if (profile.value.bio.isNotEmpty()) {
                    BioCard(profile.value.bio)
                }
            }

            // Interests Section
            item {
                if (profile.value.interests.isNotEmpty()) {
                    InterestsCard(profile.value.interests)
                }
            }

            // Skills Section
            item {
                if (profile.value.skills.isNotEmpty()) {
                    SkillsCard(profile.value.skills)
                }
            }

            // Clubs Section
            item {
                if (profile.value.clubs.isNotEmpty()) {
                    ClubsCard(profile.value.clubs)
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Profile header for viewing other user's profile.
 */
@Composable
private fun OtherUserProfileHeader(profile: UserProfileData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("👤", style = MaterialTheme.typography.headlineLarge)
            }

            // Name
            Text(
                text = profile.fullName,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            // Username
            Text(
                text = "@${profile.username}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            // Verification Badge
            if (profile.isVerified) {
                Text("✓ Verified", style = MaterialTheme.typography.labelSmall)
            }

            // University & Major
            if (profile.university.isNotEmpty()) {
                Text(
                    text = profile.university,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (profile.major.isNotEmpty()) {
                Text(
                    text = profile.major,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Bio card.
 */
@Composable
private fun BioCard(bio: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
            Text(text = bio, style = MaterialTheme.typography.bodySmall)
        }
    }
}

/**
 * Interests card.
 */
@Composable
private fun InterestsCard(interests: List<String>) {
    TagsCard("Interests", interests)
}

/**
 * Skills card.
 */
@Composable
private fun SkillsCard(skills: List<String>) {
    TagsCard("Skills", skills)
}

/**
 * Clubs card.
 */
@Composable
private fun ClubsCard(clubs: List<String>) {
    TagsCard("Clubs & Organizations", clubs)
}

/**
 * Generic tags card.
 */
@Composable
private fun TagsCard(title: String, items: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                items.forEach { item ->
                    AssistChip(
                        onClick = { },
                        label = { Text(item, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }
    }
}