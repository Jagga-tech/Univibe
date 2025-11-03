package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.components.social.CommentItem
import com.example.univibe.ui.screens.home.PostCard
import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.*
import com.example.univibe.util.ShareHelper
import java.util.UUID

data class PostDetailScreen(val postId: String) : Screen {
    @Composable
    override fun Content() {
        PostDetailScreenContent(postId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostDetailScreenContent(postId: String) {
    val navigator = LocalNavigator.currentOrThrow
    
    // Get post data
    val post = remember { MockPosts.getPostById(postId) }
    var postState by remember { mutableStateOf(post) }
    
    // Comments state
    var comments by remember { mutableStateOf(MockComments.getCommentsForPost(postId)) }
    var commentText by remember { mutableStateOf("") }
    var isPosting by remember { mutableStateOf(false) }
    
    if (postState == null) {
        // Post not found
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Post Not Found") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Post not found", style = MaterialTheme.typography.bodyLarge)
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            CommentInputBar(
                value = commentText,
                onValueChange = { commentText = it },
                onSendClick = {
                    if (commentText.isNotBlank()) {
                        isPosting = true
                        
                        // Create new comment
                        val newComment = Comment(
                            id = UUID.randomUUID().toString(),
                            postId = postId,
                            authorId = "1", // Current user
                            author = MockUsers.users[0], // Current user
                            content = commentText,
                            likeCount = 0,
                            isLiked = false,
                            createdAt = System.currentTimeMillis(),
                            replyCount = 0
                        )
                        
                        // Add comment
                        comments = comments + newComment
                        
                        // Update post comment count
                        postState = postState?.copy(
                            commentCount = postState!!.commentCount + 1
                        )
                        
                        // Clear input
                        commentText = ""
                        isPosting = false
                    }
                },
                enabled = !isPosting
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
        ) {
            // Original post
            item {
                PostCard(
                    id = postState!!.id,
                    userName = postState!!.author.fullName,
                    userAvatarUrl = postState!!.author.avatarUrl,
                    timestamp = formatTimestamp(postState!!.createdAt),
                    content = postState!!.content,
                    likeCount = postState!!.likeCount,
                    commentCount = postState!!.commentCount,
                    isLiked = postState!!.isLiked,
                    onLikeClick = {
                        postState = postState?.copy(
                            isLiked = !postState!!.isLiked,
                            likeCount = if (postState!!.isLiked) 
                                postState!!.likeCount - 1 
                            else 
                                postState!!.likeCount + 1
                        )
                    },
                    onCommentClick = { /* Already on detail screen */ },
                    onShareClick = { 
                        val shareText = ShareHelper.sharePost(postState!!)
                        println("Share: $shareText")
                    },
                    onMoreClick = { /* TODO: More options */ }
                )
            }
            
            // Divider
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.Spacing.md)
                )
            }
            
            // Comments header
            item {
                Text(
                    text = "Comments (${comments.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = Dimensions.Spacing.md)
                )
            }
            
            // Comments list
            if (comments.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Spacing.xl),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                        ) {
                            Text(
                                text = "No comments yet",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Be the first to comment!",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(
                    items = comments,
                    key = { comment -> comment.id }
                ) { comment ->
                    CommentItem(
                        comment = comment,
                        onLikeClick = { updatedComment ->
                            comments = comments.map {
                                if (it.id == updatedComment.id) updatedComment else it
                            }
                        },
                        onReplyClick = { /* TODO: Reply to comment */ },
                        onUserClick = { userId ->
                            navigator.push(UserProfileScreen(userId))
                        }
                    )
                }
            }
            
            // Bottom padding for nav bar
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Comment input bar component at the bottom of the screen.
 */
@Composable
private fun CommentInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Add a comment...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(48.dp),
                enabled = enabled,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                textStyle = MaterialTheme.typography.bodySmall
            )
            
            IconButton(
                onClick = onSendClick,
                enabled = enabled && value.isNotBlank(),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send comment",
                    tint = if (enabled && value.isNotBlank())
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

/**
 * Format timestamp to relative time string.
 */
private fun formatTimestamp(createdAt: Long): String {
    val currentTime = System.currentTimeMillis()
    val differenceInMillis = currentTime - createdAt
    
    return when {
        differenceInMillis < 60000 -> "now"
        differenceInMillis < 3600000 -> "${differenceInMillis / 60000}m ago"
        differenceInMillis < 86400000 -> "${differenceInMillis / 3600000}h ago"
        differenceInMillis < 604800000 -> "${differenceInMillis / 86400000}d ago"
        else -> "${differenceInMillis / 604800000}w ago"
    }
}