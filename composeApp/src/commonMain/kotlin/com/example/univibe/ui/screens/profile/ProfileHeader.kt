package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a user's profile information.
 *
 * @param id Unique identifier for the user
 * @param name User's full name
 * @param bio User's bio/about section
 * @param avatarUrl Optional URL for user's avatar
 * @param department User's department or school
 * @param joinDate Date user joined (e.g., "Joined Jan 2024")
 * @param followerCount Number of followers
 * @param followingCount Number of following
 * @param postsCount Number of posts created
 */
data class UserProfile(
    val id: String,
    val name: String,
    val bio: String,
    val avatarUrl: String? = null,
    val department: String,
    val joinDate: String,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val postsCount: Int = 0
)

/**
 * Data class for displaying a stat (count + label).
 */
data class ProfileStat(
    val count: Int,
    val label: String
)

/**
 * Profile header component displaying user info and stats.
 * Shows avatar, name, bio, department, and engagement stats.
 *
 * @param profile User profile data
 * @param isOwnProfile Whether this is the current user's profile
 * @param onEditClick Callback when edit button is clicked (only for own profile)
 * @param onFollowClick Callback when follow/unfollow button is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun ProfileHeader(
    profile: UserProfile,
    isOwnProfile: Boolean = true,
    onEditClick: () -> Unit = {},
    onFollowClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
        ) {
            // Avatar
            UserAvatar(
                imageUrl = profile.avatarUrl,
                size = Dimensions.AvatarSize.xlarge
            )

            // Name
            Text(
                text = profile.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // Department
            Text(
                text = profile.department,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            // Bio
            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // Join date
            Text(
                text = profile.joinDate,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))

            // Stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileStatItem(
                    count = profile.postsCount,
                    label = "Posts"
                )

                ProfileStatItem(
                    count = profile.followerCount,
                    label = "Followers"
                )

                ProfileStatItem(
                    count = profile.followingCount,
                    label = "Following"
                )
            }
        }
    }
}

/**
 * Single stat display component.
 */
@Composable
private fun ProfileStatItem(
    count: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
        modifier = modifier
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}