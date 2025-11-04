package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.OutlineButton
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Profile screen composable - view and edit user profile.
 * Displays user info, stats, interests, groups, and settings.
 *
 * @param profile User profile data
 * @param sections Profile sections (interests, groups, skills, etc.)
 * @param isOwnProfile Whether this is the current user's profile
 * @param onEditProfileClick Callback when edit profile is clicked
 * @param onEditSectionClick Callback when edit button in a section is clicked
 * @param onFollowClick Callback when follow/unfollow button is clicked
 * @param onLogoutClick Callback when logout button is clicked
 * @param onSettingsClick Callback when settings is clicked
 * @param onBackClick Callback when back button is clicked
 */
@Composable
fun ProfileScreen(
    profile: UserProfile = getSampleProfile(),
    sections: List<Pair<String, List<ProfileSectionItem>>> = getSampleProfileSections(),
    isOwnProfile: Boolean = true,
    onEditProfileClick: () -> Unit = {},
    onEditSectionClick: (String) -> Unit = {},
    onFollowClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with navigation icons
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isOwnProfile) "My Profile" else "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                if (isOwnProfile) {
                    IconButton(
                        onClick = onLogoutClick,
                        modifier = Modifier.size(Dimensions.IconSize.large)
                    ) {
                        TextIcon(
                            symbol = UISymbols.LOGOUT,
                            contentDescription = "Logout",
                            fontSize = 20,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Profile header
        item {
            ProfileHeader(
                profile = profile,
                isOwnProfile = isOwnProfile,
                onEditClick = onEditProfileClick,
                onFollowClick = onFollowClick,
                modifier = Modifier.padding(Dimensions.Spacing.md)
            )
        }

        // Action buttons (edit profile or follow/message)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
            ) {
                if (isOwnProfile) {
                    PrimaryButton(
                        text = "Edit Profile",
                        onClick = onEditProfileClick,
                        modifier = Modifier.weight(1f)
                    )

                    OutlineButton(
                        text = "Settings",
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    PrimaryButton(
                        text = "Follow",
                        onClick = onFollowClick,
                        modifier = Modifier.weight(1f)
                    )

                    OutlineButton(
                        text = "Message",
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Profile sections (interests, groups, skills, etc.)
        item {
            ProfileSectionsGrid(
                sections = sections,
                isOwnProfile = isOwnProfile,
                onSectionEditClick = onEditSectionClick
            )
        }

        // Additional info sections
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
            ) {
                // Recent activity section
                if (isOwnProfile) {
                    RecentActivitySection()
                }

                // About section
                AboutSection(
                    bio = profile.bio,
                    department = profile.department
                )
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Recent activity section showing latest user activity.
 */
@Composable
private fun RecentActivitySection() {
    ProfileSection(
        title = "Recent Activity",
        items = listOf(
            ProfileSectionItem(id = "act_1", title = "Posted about study tips", subtitle = "2 hours ago"),
            ProfileSectionItem(id = "act_2", title = "Joined Tennis Team", subtitle = "1 day ago"),
            ProfileSectionItem(id = "act_3", title = "Commented on event", subtitle = "3 days ago")
        ),
        isEditable = false
    )
}

/**
 * About section showing profile description.
 */
@Composable
private fun AboutSection(
    bio: String,
    department: String
) {
    ProfileSection(
        title = "About",
        items = listOf(
            ProfileSectionItem(id = "bio", title = bio),
            ProfileSectionItem(id = "dept", title = "Department: $department")
        ),
        isEditable = false
    )
}

/**
 * Convenience function to create sample profile for preview and testing.
 */
fun getSampleProfile(): UserProfile = UserProfile(
    id = "user_profile_1",
    name = "Alex Chen",
    bio = "Computer Science student passionate about web development and AI. Always up for collaborating on projects!",
    department = "School of Engineering",
    joinDate = "Joined January 2024",
    avatarUrl = null,
    followerCount = 234,
    followingCount = 189,
    postsCount = 47
)