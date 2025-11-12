package com.example.univibe.ui.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.domain.models.Post
import kotlin.math.absoluteValue

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
                        name = userName
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
                    TextIcon(
                        symbol = UISymbols.MORE_VERT,
                        contentDescription = "More options",
                        fontSize = 20,
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
                    icon = if (liked) PlatformIcons.Favorite else PlatformIcons.FavoriteBorder,
                    label = "Like",
                    iconTint = if (liked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    scale = likeScale,
                    onClick = {
                        liked = !liked
                        onLikeClick(id)
                    }
                )

                ActionButton(
                    icon = PlatformIcons.ChatBubbleOutline,
                    label = "Comment",
                    onClick = { onCommentClick(id) }
                )

                ActionButton(
                    icon = PlatformIcons.Share,
                    label = "Share",
                    onClick = { onShareClick(id) }
                )
            }
        }
    }
}

/**
 * PostCard overload that accepts the Post domain model.
 * Converts domain model to UI representation with timestamp formatting.
 *
 * @param post The Post domain model to display
 * @param onLikeClick Callback when like button is clicked
 * @param onCommentClick Callback when comment button is clicked
 * @param onShareClick Callback when share button is clicked
 * @param onPostClick Callback when the post content is clicked
 * @param onUserClick Callback when the user header is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun PostCard(
    post: Post,
    onLikeClick: (Post) -> Unit = {},
    onCommentClick: (Post) -> Unit = {},
    onShareClick: (Post) -> Unit = {},
    onPostClick: () -> Unit = {},
    onUserClick: (Post) -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Format relative time
    val timeAgo = formatRelativeTime(post.createdAt)
    
    var liked by remember { mutableStateOf(post.isLiked) }
    var likeScale by remember { mutableStateOf(1f) }
    var currentLikeCount by remember { mutableStateOf(post.likeCount) }
    
    // Animate like icon scale on change
    LaunchedEffect(liked) {
        if (liked) {
            likeScale = 1.3f
            kotlinx.coroutines.delay(150)
            likeScale = 1f
        }
    }

    UniVibeCard(
        modifier = modifier
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
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onUserClick(post) }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    UserAvatar(
                        imageUrl = post.author.avatarUrl,
                        size = Dimensions.AvatarSize.small,
                        name = post.author.fullName
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = post.author.fullName,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        // Subtitle with course or timestamp
                        if (post.course != null) {
                            Text(
                                text = post.course,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                        }
                        
                        Text(
                            text = timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = { /* TODO: More options */ },
                    modifier = Modifier.size(Dimensions.IconSize.medium)
                ) {
                    TextIcon(
                        symbol = UISymbols.MORE_VERT,
                        contentDescription = "More options",
                        fontSize = 20,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.default))

            // Achievement badge if present
            if (post.achievement != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(Dimensions.Spacing.sm)
                ) {
                    Text(
                        text = "🏆 ${post.achievement.title}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(Dimensions.Spacing.sm)
                    )
                }
                Spacer(modifier = Modifier.height(Dimensions.Spacing.default))
            }

            // Post content
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onPostClick)
            )

            // Tags
            if (post.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    post.tags.take(3).forEach { tag ->
                        Text(
                            text = "#$tag",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (post.tags.size > 3) {
                        Text(
                            text = "+${post.tags.size - 3}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.default))

            // Engagement metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currentLikeCount > 0) "$currentLikeCount likes" else "Be the first to like",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (post.commentCount > 0) {
                        Text(
                            text = "${post.commentCount} comments",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    if (post.shareCount > 0) {
                        Text(
                            text = "${post.shareCount} shares",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
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
                    icon = if (liked) PlatformIcons.Favorite else PlatformIcons.FavoriteBorder,
                    label = "Like",
                    iconTint = if (liked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    scale = likeScale,
                    onClick = {
                        liked = !liked
                        currentLikeCount = if (liked) currentLikeCount + 1 else (currentLikeCount - 1).coerceAtLeast(0)
                        onLikeClick(post)
                    }
                )

                ActionButton(
                    icon = PlatformIcons.ChatBubbleOutline,
                    label = "Comment",
                    onClick = { onCommentClick(post) }
                )

                ActionButton(
                    icon = PlatformIcons.Share,
                    label = "Share",
                    onClick = { onShareClick(post) }
                )
            }
        }
    }
}

/**
 * Helper function to format absolute timestamp into relative time string.
 */
private fun formatRelativeTime(timestamp: Long): String {
    val currentTime = 1698000000000L // Same as mock data base time
    val diffMs = currentTime - timestamp
    
    return when {
        diffMs < 60000 -> "now"
        diffMs < 3600000 -> "${diffMs / 60000}m ago"
        diffMs < 86400000 -> "${diffMs / 3600000}h ago"
        diffMs < 604800000 -> "${diffMs / 86400000}d ago"
        else -> "${diffMs / 604800000}w ago"
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