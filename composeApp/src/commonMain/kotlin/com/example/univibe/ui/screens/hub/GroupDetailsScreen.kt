package com.example.univibe.ui.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.OutlineButton
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a group moderator/admin.
 *
 * @param id Unique identifier for the moderator
 * @param name Name of the moderator
 * @param avatarUrl Optional URL for the moderator's avatar
 * @param title Role/title of the moderator (e.g., "Founder", "Admin")
 */
data class GroupModerator(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val title: String = "Moderator"
)

/**
 * Group details screen composable - shows full group information and join options.
 * Displays group cover, details, members, rules, and allows user to join/leave.
 *
 * @param group The group to display details for
 * @param members List of member avatars to display
 * @param moderators List of group moderators/admins
 * @param onBackClick Callback when back button is clicked
 * @param onJoinClick Callback when join/leave button is clicked (provides new joined state)
 * @param onShareClick Callback when share button is clicked
 * @param onMembersClick Callback when "View all members" is clicked
 * @param onModeratorClick Callback when moderator card is clicked
 */
@Composable
fun GroupDetailsScreen(
    group: GroupItem = getSampleGroups()[0],
    members: List<String> = getSampleGroupMembers(),
    moderators: List<GroupModerator> = getSampleGroupModerators(),
    onBackClick: () -> Unit = {},
    onJoinClick: (Boolean) -> Unit = {},
    onShareClick: () -> Unit = {},
    onMembersClick: () -> Unit = {},
    onModeratorClick: () -> Unit = {}
) {
    var isJoined by remember { mutableStateOf(group.isJoined) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with back and action buttons
        GroupDetailsHeader(
            group = group,
            onBackClick = onBackClick,
            onShareClick = onShareClick
        )

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Group cover placeholder
            item {
                GroupCoverPlaceholder()
            }

            // Group title and category
            item {
                GroupTitleSection(group = group)
            }

            // Group info (members, category, description)
            item {
                GroupInfoSection(group = group)
            }

            // Group description/about
            item {
                GroupDescriptionSection(group = group)
            }

            // Group moderators
            item {
                ModeratorsSection(
                    moderators = moderators,
                    onClick = onModeratorClick
                )
            }

            // Group members preview
            item {
                MembersSection(
                    members = members,
                    totalMemberCount = group.memberCount,
                    onViewAllClick = onMembersClick
                )
            }

            // Group rules/guidelines
            item {
                GroupRulesSection()
            }

            // Bottom padding
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }

        // Join/Leave action button
        JoinGroupActionButton(
            isJoined = isJoined,
            onJoinClick = { newJoinedState ->
                isJoined = newJoinedState
                onJoinClick(newJoinedState)
            }
        )
    }
}

/**
 * Header with back button and action icons.
 */
@Composable
private fun GroupDetailsHeader(
    group: GroupItem,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = group.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimensions.Spacing.md)
        )

        IconButton(
            onClick = onShareClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share group",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Group cover placeholder.
 */
@Composable
private fun GroupCoverPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🏛️ Group Cover",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Group title section with category badge.
 */
@Composable
private fun GroupTitleSection(group: GroupItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
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
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = group.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Group name
        Text(
            text = group.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Group info section showing members count and category.
 */
@Composable
private fun GroupInfoSection(group: GroupItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.lg)
    ) {
        GroupInfoRow(
            icon = Icons.Default.People,
            label = "Members",
            value = group.memberCount.toString(),
            modifier = Modifier.weight(1f)
        )

        GroupInfoRow(
            icon = Icons.Default.Info,
            label = "Category",
            value = group.category,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Helper composable for group info rows with icons.
 */
@Composable
private fun GroupInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(Dimensions.IconSize.medium),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Group description section.
 */
@Composable
private fun GroupDescriptionSection(group: GroupItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Text(
            text = "About this group",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = group.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = (MaterialTheme.typography.bodyMedium.lineHeight * 1.2)
        )
    }
}

/**
 * Group moderators section.
 */
@Composable
private fun ModeratorsSection(
    moderators: List<GroupModerator>,
    onClick: () -> Unit = {}
) {
    if (moderators.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Text(
            text = "Moderators",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
        ) {
            moderators.forEach { moderator ->
                ModeratorCard(
                    moderator = moderator,
                    onClick = onClick
                )
            }
        }
    }
}

/**
 * Individual moderator card.
 */
@Composable
private fun ModeratorCard(
    moderator: GroupModerator,
    onClick: () -> Unit = {}
) {
    UniVibeCard(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                imageUrl = moderator.avatarUrl,
                size = Dimensions.AvatarSize.medium
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                Text(
                    text = moderator.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = moderator.title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "View profile",
                modifier = Modifier
                    .size(Dimensions.IconSize.medium)
                    .rotate(180f),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Group members preview section.
 */
@Composable
private fun MembersSection(
    members: List<String>,
    totalMemberCount: Int,
    onViewAllClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Members",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (totalMemberCount > members.size) {
                OutlineButton(
                    text = "View all",
                    onClick = onViewAllClick,
                    modifier = Modifier
                        .height(32.dp)
                        .padding(0.dp),
                    textStyle = MaterialTheme.typography.labelSmall
                )
            }
        }

        if (members.isNotEmpty()) {
            // Avatar stack showing first 5 members
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                members.take(5).forEachIndexed { index, memberId ->
                    UserAvatar(
                        imageUrl = memberId,
                        size = 48.dp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .offset(x = (index * 36).dp)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(2.dp)
                    )
                }

                // +X more badge if there are additional members
                if (totalMemberCount > members.size) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .background(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = androidx.compose.foundation.shape.CircleShape
                            )
                            .size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+${totalMemberCount - members.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.md),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Be the first to join this group!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Group rules and guidelines section.
 */
@Composable
private fun GroupRulesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Text(
            text = "Group Rules",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        UniVibeCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
            ) {
                RuleItem("Be Respectful", "Treat all members with courtesy and respect.")
                RuleItem("Stay On Topic", "Keep discussions relevant to the group's purpose.")
                RuleItem("No Spam", "Avoid promotional content and duplicate posts.")
                RuleItem("Follow University Policy", "All campus rules and regulations apply here.")
            }
        }
    }
}

/**
 * Individual rule item.
 */
@Composable
private fun RuleItem(title: String, description: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
    ) {
        Text(
            text = "• $title",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Join/Leave action button for the group.
 */
@Composable
private fun JoinGroupActionButton(
    isJoined: Boolean,
    onJoinClick: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        if (isJoined) {
            OutlineButton(
                text = "Leave Group",
                onClick = { onJoinClick(false) },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            )
        }

        PrimaryButton(
            text = if (isJoined) "✓ You're a member" else "+ Join Group",
            onClick = { onJoinClick(!isJoined) },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            enabled = true
        )
    }
}

/**
 * Convenience function to create sample group members for preview and testing.
 */
fun getSampleGroupMembers(): List<String> = listOf(
    "https://api.example.com/avatar/user_1.jpg",
    "https://api.example.com/avatar/user_2.jpg",
    "https://api.example.com/avatar/user_3.jpg",
    "https://api.example.com/avatar/user_4.jpg",
    "https://api.example.com/avatar/user_5.jpg"
)

/**
 * Convenience function to create sample group moderators for preview and testing.
 */
fun getSampleGroupModerators(): List<GroupModerator> = listOf(
    GroupModerator(
        id = "mod_1",
        name = "Sarah Chen",
        title = "Founder & Admin"
    ),
    GroupModerator(
        id = "mod_2",
        name = "James Rodriguez",
        title = "Moderator"
    )
)