package com.example.univibe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.univibe.domain.models.Post
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.FeedScreen
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.components.LoadingIndicator
import com.example.univibe.data.mock.MockPosts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Home Screen using UniVibe Design System
 * Professional, clean, and consistent with the app's design language
 */
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit = {},
    onRefresh: (() -> Unit)? = null
) {
    var posts by remember { mutableStateOf<List<Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    
    // Load initial data
    LaunchedEffect(Unit) {
        delay(1000) // Simulate network call
        posts = MockPosts.posts
        isLoading = false
    }
    
    val refreshData: () -> Unit = {
        scope.launch {
            isLoading = true
            delay(1000)
            posts = MockPosts.posts.shuffled() // Simulate new content
            isLoading = false
        }
        Unit
    }
    
    if (isLoading) {
        UniVibeDesign.LoadingState(
            modifier = Modifier.fillMaxSize(),
            message = "Loading your feed..."
        )
    } else {
        FeedScreen(
            items = posts,
            onRefresh = onRefresh ?: refreshData,
            itemContent = { post ->
                PostCard(
                    post = post,
                    onLikeClick = { /* Handle like */ },
                    onCommentClick = { onNavigate("post/${post.id}") },
                    onShareClick = { /* Handle share */ },
                    onProfileClick = { onNavigate("profile/${post.author.id}") }
                )
            },
            emptyStateContent = {
                UniVibeDesign.EmptyState(
                    icon = { 
                        Icon(
                            Icons.Outlined.Feed, 
                            null, 
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        ) 
                    },
                    title = "Welcome to UniVibe!",
                    description = "Your campus feed will appear here. Follow some people to get started!"
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onNavigate("create/post") },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, "Create post")
                }
            }
        )
    }
}

/**
 * Modern Post Card with consistent design system styling
 */
@Composable
private fun PostCard(
    post: Post,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    UniVibeDesign.StandardCard(
        onClick = onCommentClick
    ) {
        // Post header with user info
        PostHeader(
            authorName = post.author.fullName,
            authorHandle = "@${post.author.username}",
            timestamp = "2h ago", // TODO: Calculate from post.createdAt
            onProfileClick = onProfileClick,
            onMoreClick = { /* Handle more menu */ }
        )
        
        // Post content
        Text(
            text = post.content,
            style = UniVibeDesign.Text.body(),
            modifier = Modifier.padding(top = UniVibeDesign.Spacing.sm)
        )
        
        // Engagement stats
        if (post.likeCount > 0 || post.commentCount > 0) {
            PostStats(
                likeCount = post.likeCount,
                commentCount = post.commentCount,
                modifier = Modifier.padding(top = UniVibeDesign.Spacing.sm)
            )
        }
        
        // Action buttons
        PostActions(
            isLiked = post.isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick,
            modifier = Modifier.padding(top = UniVibeDesign.Spacing.sm)
        )
    }
}

/**
 * Post header with user avatar, name, and metadata
 */
@Composable
private fun PostHeader(
    authorName: String,
    authorHandle: String,
    timestamp: String,
    onProfileClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
    ) {
        // User avatar
        UserAvatar(
            imageUrl = null, // TODO: Get from author.avatarUrl
            size = 40.dp,
            modifier = Modifier.clickable { onProfileClick() }
        )
        
        // User info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = authorName,
                style = UniVibeDesign.Text.cardTitle(),
                modifier = Modifier.clickable { onProfileClick() }
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = authorHandle,
                    style = UniVibeDesign.Text.caption()
                )
                Text(
                    text = "•",
                    style = UniVibeDesign.Text.caption()
                )
                Text(
                    text = timestamp,
                    style = UniVibeDesign.Text.caption()
                )
            }
        }
        
        // More menu
        IconButton(onClick = onMoreClick) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Post engagement statistics
 */
@Composable
private fun PostStats(
    likeCount: Int,
    commentCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
    ) {
        if (likeCount > 0) {
            Text(
                text = "$likeCount ${if (likeCount == 1) "like" else "likes"}",
                style = UniVibeDesign.Text.caption()
            )
        }
        if (commentCount > 0) {
            Text(
                text = "$commentCount ${if (commentCount == 1) "comment" else "comments"}",
                style = UniVibeDesign.Text.caption()
            )
        }
    }
}

/**
 * Post interaction buttons
 */
@Composable
private fun PostActions(
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Divider
    HorizontalDivider(
        modifier = Modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        color = MaterialTheme.colorScheme.outlineVariant
    )
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PostActionButton(
            icon = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            label = "Like",
            onClick = onLikeClick,
            tint = if (isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        PostActionButton(
            icon = Icons.Outlined.ChatBubbleOutline,
            label = "Comment",
            onClick = onCommentClick
        )
        
        PostActionButton(
            icon = Icons.Outlined.Share,
            label = "Share",
            onClick = onShareClick
        )
    }
}

/**
 * Individual post action button
 */
@Composable
private fun PostActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    tint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = Modifier
            .clip(UniVibeDesign.Cards.smallShape)
            .clickable { onClick() }
            .padding(
                horizontal = UniVibeDesign.Spacing.md,
                vertical = UniVibeDesign.Spacing.sm
            ),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = tint
        )
        Text(
            text = label,
            style = UniVibeDesign.Text.caption().copy(
                color = tint,
                fontWeight = FontWeight.Medium
            )
        )
    }
}