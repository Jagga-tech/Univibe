package com.example.univibe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockPosts
import com.example.univibe.data.mock.MockStories
import com.example.univibe.domain.models.Post
import com.example.univibe.ui.components.*
import com.example.univibe.ui.screens.create.*
import com.example.univibe.ui.screens.detail.*
import com.example.univibe.ui.screens.features.*
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.util.ShareHelper
import com.example.univibe.ui.utils.OnBottomReached
import com.example.univibe.ui.utils.PaginationState
import com.example.univibe.ui.utils.rememberPaginationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Data class representing a post in the feed.
 */
data class PostItem(
    val id: String,
    val userName: String,
    val userAvatarUrl: String? = null,
    val timestamp: String,
    val content: String,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isLiked: Boolean = false
)

/**
 * Home screen composable - main feed for UniVibe app.
 * Features:
 * - Pull-to-refresh for manual refresh
 * - Infinite scroll/pagination
 * - Skeleton loading for initial load
 * - Error state with retry
 * - Empty state when no posts
 */
@Composable
fun HomeScreen(
    posts: List<PostItem> = emptyList(),
    stories: List<StoryItem> = emptyList(),
    quickAccessItems: List<QuickAccessItem> = getDefaultQuickAccessItems(),
    onPostLikeClick: (String) -> Unit = {},
    onPostCommentClick: (String) -> Unit = {},
    onPostShareClick: (String) -> Unit = {},
    onPostMoreClick: (String) -> Unit = {},
    onStoryClick: (String) -> Unit = {},
    onAddStoryClick: () -> Unit = {},
    onQuickAccessClick: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val navigator = LocalNavigator.currentOrThrow
    var searchQuery by remember { mutableStateOf("") }
    
    // Pagination state
    val paginationState = rememberPaginationState(
        initialItems = MockPosts.posts.take(10)
    )
    
    // Pull-to-refresh state
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    
    val listState = rememberLazyListState()
    
    // Initial loading
    var isInitialLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(500) // Simulate network call
        isInitialLoading = false
    }
    
    // Pull-to-refresh handler
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshing = true
            delay(1000) // Simulate network call
            paginationState.refresh(MockPosts.posts.take(10))
            isRefreshing = false
            pullToRefreshState.endRefresh()
        }
    }
    
    // Pagination handler
    listState.OnBottomReached {
        scope.launch {
            paginationState.loadNextPage { page ->
                delay(1000) // Simulate network call
                MockPosts.posts.drop((page + 1) * 10).take(10)
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        if (isInitialLoading) {
            // Skeleton loading state
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(3) {
                    PostCardSkeleton()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } else if (paginationState.error != null) {
            // Error state
            ErrorState(
                error = paginationState.error ?: "Unknown error",
                onRetry = {
                    scope.launch {
                        paginationState.retry { page ->
                            delay(1000)
                            MockPosts.posts.drop((page + 1) * 10).take(10)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                state = listState,
                contentPadding = PaddingValues(16.dp)
            ) {
                // Top section with search bar
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Home",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UniVibeTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = "What's on your mind?",
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                            )
                            
                            IconButton(
                                onClick = { 
                                    scope.launch {
                                        pullToRefreshState.startRefresh()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = PlatformIcons.Refresh,
                                    contentDescription = "Refresh"
                                )
                            }
                        }
                    }
                }
                
                // Posts list
                if (paginationState.items.isEmpty()) {
                    item {
                        EmptyState(
                            title = "No posts yet",
                            description = "Be the first to share something with your campus!",
                            icon = PlatformIcons.SearchOff,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    items(paginationState.items) { post ->
                        PostCard(
                            post = post,
                            onLikeClick = { onPostLikeClick(post.id) },
                            onCommentClick = { onPostCommentClick(post.id) },
                            onShareClick = { onPostShareClick(post.id) },
                            onMoreClick = { onPostMoreClick(post.id) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                
                // Loading indicator at bottom
                if (paginationState.isLoading && paginationState.items.isNotEmpty()) {
                    item {
                        PaginationLoadingIndicator()
                    }
                }
                
                // End of list message
                if (!paginationState.hasMorePages && paginationState.items.isNotEmpty()) {
                    item {
                        EndOfListIndicator()
                    }
                }
            }
        }
        
        // Pull-to-refresh indicator
            //         PullToRefreshContainer(
            //             state = pullToRefreshState,
            //             modifier = Modifier.align(Alignment.TopCenter)
    }
}

@Composable
private fun PostCard(
    post: PostItem,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onMoreClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // User header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar placeholder
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = androidx.compose.foundation.shape.CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.userName,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = post.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onMoreClick) {
                    Icon(PlatformIcons.MoreVert, contentDescription = "More")
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Content text
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Engagement stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${post.likeCount} likes",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${post.commentCount} comments",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActionButton(
                    icon = if (post.isLiked) PlatformIcons.Favorite else PlatformIcons.FavoriteBorder,
                    label = "Like",
                    onClick = onLikeClick,
                    modifier = Modifier.weight(1f)
                )
                ActionButton(
                    icon = PlatformIcons.ChatBubbleOutline,
                    label = "Comment",
                    onClick = onCommentClick,
                    modifier = Modifier.weight(1f)
                )
                ActionButton(
                    icon = PlatformIcons.IosShare,
                    label = "Share",
                    onClick = onShareClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(36.dp)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

data class StoryItem(
    val id: String,
    val userName: String,
    val avatarUrl: String? = null
)

data class QuickAccessItem(
    val id: String,
    val label: String,
    val icon: String
)

fun getDefaultQuickAccessItems() = emptyList<QuickAccessItem>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberPullToRefreshState(): androidx.compose.material3.pulltorefresh.PullToRefreshState {
    return androidx.compose.material3.pulltorefresh.rememberPullToRefreshState()
}
