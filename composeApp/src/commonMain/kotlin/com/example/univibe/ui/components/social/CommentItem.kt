package com.example.univibe.ui.components.social

import com.example.univibe.util.getCurrentTimeMillis
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.univibe.domain.models.Comment
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import kotlin.math.absoluteValue

/**
 * Individual comment card component for post detail comments section.
 * Displays user info, comment content, engagement metrics, and action buttons.
 *
 * @param comment The comment data to display
 * @param onLikeClick Callback when like button is clicked
 * @param onReplyClick Callback when reply button is clicked
 * @param onUserClick Callback when user avatar/name is clicked
 */
@Composable
fun CommentItem(
    comment: Comment,
    onLikeClick: (Comment) -> Unit = {},
    onReplyClick: (Comment) -> Unit = {},
    onUserClick: (String) -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(comment.isLiked) }
    var likeCount by remember { mutableStateOf(comment.likeCount) }
    var likeScale by remember { mutableStateOf(1f) }
    
    // Animate like icon scale on change
    LaunchedEffect(isLiked) {
        if (isLiked) {
            likeScale = 1.3f
            kotlinx.coroutines.delay(150)
            likeScale = 1f
        }
    }
    
    // Color animation for like button
    val likeButtonColor by animateColorAsState(
        targetValue = if (isLiked) 
            MaterialTheme.colorScheme.error 
        else 
            MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label = "likeButtonColor"
    )
    
    // Format timestamp
    val timeAgo = formatTimeAgo(comment.createdAt)
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimensions.Spacing.md,
                vertical = Dimensions.Spacing.sm
            )
    ) {
        // User avatar
        UserAvatar(
            name = comment.author.fullName,
            size = Dimensions.AvatarSize.small,
            modifier = Modifier.clickable { onUserClick(comment.authorId) }
        )
        
        Spacer(modifier = Modifier.width(Dimensions.Spacing.md))
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Author name and timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = comment.author.fullName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable { onUserClick(comment.authorId) }
                    )
                    
                    Spacer(modifier = Modifier.width(Dimensions.Spacing.xs))
                    
                    Text(
                        text = timeAgo,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))
            
            // Comment content
            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
            
            // Action buttons (Like, Reply)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like button
                Row(
                    modifier = Modifier
                        .clickable {
                            isLiked = !isLiked
                            likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                            onLikeClick(
                                comment.copy(
                                    isLiked = isLiked,
                                    likeCount = likeCount
                                )
                            )
                        }
                        .padding(Dimensions.Spacing.xs),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isLiked) PlatformIcons.Favorite else PlatformIcons.FavoriteBorder,
                        contentDescription = if (isLiked) "Unlike" else "Like",
                        tint = likeButtonColor,
                        modifier = Modifier
                            .size(16.dp)
                            .scale(likeScale)
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Spacing.xxs))
                    Text(
                        text = likeCount.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = likeButtonColor
                    )
                }
                
                Spacer(modifier = Modifier.width(Dimensions.Spacing.lg))
                
                // Reply button
                Row(
                    modifier = Modifier
                        .clickable { onReplyClick(comment) }
                        .padding(Dimensions.Spacing.xs),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = PlatformIcons.ChatBubbleOutline,
                        contentDescription = "Reply",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Spacing.xxs))
                    Text(
                        text = if (comment.replyCount > 0) "${comment.replyCount} replies" else "Reply",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Format timestamp to relative time string (e.g., "2m ago", "1h ago")
 */
private fun formatTimeAgo(createdAt: Long): String {
    val currentTime = getCurrentTimeMillis()
    val differenceInMillis = currentTime - createdAt
    
    return when {
        differenceInMillis < 60000 -> "now"
        differenceInMillis < 3600000 -> "${differenceInMillis / 60000}m ago"
        differenceInMillis < 86400000 -> "${differenceInMillis / 3600000}h ago"
        differenceInMillis < 604800000 -> "${differenceInMillis / 86400000}d ago"
        else -> "${differenceInMillis / 604800000}w ago"
    }
}