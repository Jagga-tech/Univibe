package com.example.univibe.ui.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ShareOutlined
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.animations.AnimationConstants
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Individual post card component for the home feed.
 * Displays user info, post content, engagement metrics, and action buttons.
 *
 * @param id Unique post identifier
 * @param userName Name of the user who posted
 * @param userAvatarUrl URL of user's avatar image
 * @param timestamp When the post was created (e.g., "2 hours ago")
 * @param content Main text content of the post
 * @param likeCount Number of likes on the post
 * @param commentCount Number of comments on the post
 * @param isLiked Whether the current user has liked this post
 * @param onLikeClick Callback when like button is clicked
 * @param onCommentClick Callback when comment button is clicked
 * @param onShareClick Callback when share button is clicked
 * @param onMoreClick Callback when more options button is clicked
 */
@Composable
fun PostCard(
    id: String,
    userName: String,
    userAvatarUrl: String? = null,
    timestamp: String,
    content: String,
    likeCount: Int = 0,
    commentCount: Int = 0,
    isLiked: Boolean = false,
    onLikeClick: (String) -> Unit = {},
    onCommentClick: (String) -> Unit = {},
    onShareClick: (String) -> Unit = {},
    onMoreClick: (String) -> Unit = {}
) {
    var liked by remember { mutableStateOf(isLiked) }
    var likeScale by remember { mutableStateOf(1f) }
    
    // Animate like icon scale on change
    LaunchedEffect(liked) {
        if (liked) {
            likeScale = 1.3f
            kotlinx.coroutines.delay(150)
            likeScale = 1f
        }
    }

    UniVibeCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.default)
        ) {
            // Header: Avatar, Name, Timestamp, More button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    UserAvatar(
                        imageUrl = userAvatarUrl,
                        size = Dimensions.AvatarSize.small,
                        userName = userName
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = { onMoreClick(id) },
                    modifier = Modifier.size(Dimensions.IconSize.medium)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        modifier = Modifier.size(Dimensions.IconSize.medium),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.default))

            // Post content
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.default))

            // Engagement metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (likeCount > 0) "$likeCount likes" else "Be the first to like",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (commentCount > 0) {
                    Text(
                        text = "$commentCount comments",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.default))

            // Divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionButton(
                    icon = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    label = "Like",
                    iconTint = if (liked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    scale = likeScale,
                    onClick = {
                        liked = !liked
                        onLikeClick(id)
                    }
                )

                ActionButton(
                    icon = Icons.Outlined.ChatBubbleOutline,
                    label = "Comment",
                    onClick = { onCommentClick(id) }
                )

                ActionButton(
                    icon = Icons.Outlined.ShareOutlined,
                    label = "Share",
                    onClick = { onShareClick(id) }
                )
            }
        }
    }
}

/**
 * Reusable action button for post interactions (like, comment, share).
 */
@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    iconTint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant,
    scale: Float = 1f,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.33f)
            .then(Modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(Dimensions.IconSize.medium)
                .scale(scale)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = iconTint
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}