package com.example.univibe.ui.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a campus group or club.
 *
 * @param id Unique identifier for the group
 * @param name Name of the group/club
 * @param description Brief description of what the group does
 * @param category Category/type of the group (e.g., "Academic", "Sports", "Social")
 * @param memberCount Current number of members
 * @param groupAvatarUrl Optional URL for the group's avatar/logo
 * @param isJoined Whether the current user has joined this group
 */
data class GroupItem(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val memberCount: Int,
    val groupAvatarUrl: String? = null,
    val isJoined: Boolean = false
)

/**
 * Individual group card component for displaying campus groups/clubs.
 * Shows group info and join/leave button.
 *
 * @param item The group item data
 * @param onJoinClick Callback when join/leave button is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun GroupCard(
    item: GroupItem,
    onJoinClick: (String, Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var isJoined by remember { mutableStateOf(item.isJoined) }

    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .then(Modifier),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            // Header with avatar and basic info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.Top
            ) {
                // Group avatar
                UserAvatar(
                    imageUrl = item.groupAvatarUrl,
                    size = Dimensions.AvatarSize.medium
                )

                // Group info
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = Dimensions.Spacing.xs),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
                ) {
                    // Group name
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2
                    )

                    // Category badge
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                    Dimensions.CornerRadius.small
                                )
                            )
                            .padding(
                                horizontal = Dimensions.Spacing.sm,
                                vertical = Dimensions.Spacing.xs
                            )
                    ) {
                        Text(
                            text = item.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    // Member count
                    Text(
                        text = "${item.memberCount} members",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Join/Leave button
            PrimaryButton(
                text = if (isJoined) "✓ Joined" else "+ Join Group",
                onClick = {
                    isJoined = !isJoined
                    onJoinClick(item.id, isJoined)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            )
        }
    }
}

/**
 * Vertical list of group cards.
 * Used to display multiple campus groups.
 *
 * @param groups List of group items to display
 * @param onJoinClick Callback when join/leave button is clicked
 * @param modifier Modifier to apply to the container
 */
@Composable
fun GroupCardList(
    groups: List<GroupItem> = emptyList(),
    onJoinClick: (String, Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        groups.forEach { group ->
            GroupCard(
                item = group,
                onJoinClick = onJoinClick
            )
        }
    }
}

/**
 * Convenience function to create sample/mock groups for preview and testing.
 */
fun getSampleGroups(): List<GroupItem> = listOf(
    GroupItem(
        id = "group_1",
        name = "Organic Chemistry Study Circle",
        description = "Weekly sessions to tackle challenging organic chem concepts together.",
        category = "Academic",
        memberCount = 127,
        isJoined = false
    ),
    GroupItem(
        id = "group_2",
        name = "Debate Club",
        description = "Improve your speaking skills and engage in friendly debates.",
        category = "Social",
        memberCount = 89,
        isJoined = false
    ),
    GroupItem(
        id = "group_3",
        name = "Campus Tennis Team",
        description = "Competitive and casual tennis players welcome!",
        category = "Sports",
        memberCount = 56,
        isJoined = true
    ),
    GroupItem(
        id = "group_4",
        name = "Computer Science Club",
        description = "Hackatons, coding challenges, and tech talks every week.",
        category = "Academic",
        memberCount = 203,
        isJoined = false
    ),
    GroupItem(
        id = "group_5",
        name = "Photography Enthusiasts",
        description = "Share your photography and learn new techniques.",
        category = "Creative",
        memberCount = 94,
        isJoined = false
    )
)