package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.*
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.components.profile.ProfileStatsCard
import com.example.univibe.ui.screens.settings.AppSettingsScreen
import com.example.univibe.domain.models.User
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Screen displaying the current user's complete profile.
 */
object MyProfileScreen : Screen {
    @Composable
    override fun Content() {
        MyProfileScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyProfileScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val profile = remember { mutableStateOf(getCurrentUserProfile()) }
    val stats = remember { mutableStateOf(getUserActivityStats()) }
    val achievements = remember { mutableStateOf(getUserAchievements()) }
    val completeness = remember { mutableStateOf(getProfileCompleteness()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                actions = {
                    IconButton(onClick = { navigator.push(AppSettingsScreen) }) {
                        TextIcon(UISymbols.SETTINGS, contentDescription = "Settings")
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
                ProfileHeaderCard(profile.value)
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
                        text = "Edit Profile",
                        onClick = {
                            val userObj = User(
                                id = profile.value.id,
                                username = profile.value.username,
                                fullName = profile.value.fullName,
                                email = profile.value.email,
                                avatarUrl = profile.value.avatarUrl,
                                bio = profile.value.bio,
                                major = profile.value.major,
                                graduationYear = profile.value.graduationYear,
                                interests = profile.value.interests,
                                followersCount = profile.value.followersCount,
                                followingCount = profile.value.followingCount,
                                isVerified = profile.value.isVerified,
                                joinedDate = profile.value.joinedDate
                            )
                            navigator.push(EditProfileScreenData(user = userObj))
                        },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedButton(
                        onClick = { navigator.push(AppSettingsScreen) },
                        modifier = Modifier.weight(1f)
                    ) {
                        TextIcon(UISymbols.SETTINGS, contentDescription = null)
                        Spacer(modifier = Modifier.width(Dimensions.Spacing.sm))
                        Text("Settings")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Profile Completion
            item {
                ProfileCompletionCard(completeness.value)
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
                    postsCount = stats.value.postsCount,
                    followersCount = stats.value.followersCount,
                    followingCount = stats.value.followingCount
                )
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Bio Section
            item {
                if (profile.value.bio.isNotEmpty()) {
                    BioSection(profile.value.bio)
                }
            }

            // Interests Section
            item {
                if (profile.value.interests.isNotEmpty()) {
                    InterestsSection(profile.value.interests)
                }
            }

            // Skills Section
            item {
                if (profile.value.skills.isNotEmpty()) {
                    SkillsSection(profile.value.skills)
                }
            }

            // Clubs Section
            item {
                if (profile.value.clubs.isNotEmpty()) {
                    ClubsSection(profile.value.clubs)
                }
            }

            // Achievements Section
            item {
                AchievementsSection(achievements.value)
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Profile header card showing user avatar, name, and basic info.
 */
@Composable
private fun ProfileHeaderCard(profile: com.example.univibe.domain.models.UserProfileData) {
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

            // Join Date
            Text(
                text = "Joined in ${profile.joinedDate}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Profile completion progress card.
 */
@Composable
private fun ProfileCompletionCard(completeness: com.example.univibe.domain.models.ProfileCompleteness) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Profile Completion",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "${completeness.completionPercentage}%",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            LinearProgressIndicator(
                progress = { completeness.completionPercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))

            if (completeness.completionPercentage < 100) {
                Text(
                    text = "Complete your profile to unlock more features",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Text(
                    text = "✓ Your profile is complete!",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Bio section.
 */
@Composable
private fun BioSection(bio: String) {
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
 * Interests section.
 */
@Composable
private fun InterestsSection(interests: List<String>) {
    TagsSection("Interests", interests)
}

/**
 * Skills section.
 */
@Composable
private fun SkillsSection(skills: List<String>) {
    TagsSection("Skills", skills)
}

/**
 * Clubs section.
 */
@Composable
private fun ClubsSection(clubs: List<String>) {
    TagsSection("Clubs & Organizations", clubs)
}

/**
 * Generic tags section for displaying lists of strings as tags.
 */
@Composable
private fun TagsSection(title: String, items: List<String>) {
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

/**
 * Achievements section.
 */
@Composable
private fun AchievementsSection(achievements: List<com.example.univibe.domain.models.UserAchievement>) {
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
                text = "Achievements (${achievements.size})",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            
            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
            ) {
                achievements.forEach { achievement ->
                    AchievementBadge(achievement)
                }
            }
        }
    }
}

/**
 * Individual achievement badge.
 */
@Composable
private fun AchievementBadge(achievement: com.example.univibe.domain.models.UserAchievement) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
        modifier = Modifier
            .width(80.dp)
            .padding(Dimensions.Spacing.sm)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer,
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(achievement.icon, style = MaterialTheme.typography.headlineSmall)
        }
        Text(
            text = achievement.name,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}