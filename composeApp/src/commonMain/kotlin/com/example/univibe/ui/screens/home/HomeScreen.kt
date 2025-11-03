package com.example.univibe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockPosts
import com.example.univibe.data.mock.MockStories
import com.example.univibe.domain.models.Post
import com.example.univibe.ui.components.UniVibeTextField
import com.example.univibe.ui.screens.create.*
import com.example.univibe.ui.screens.detail.*
import com.example.univibe.ui.screens.features.*
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.util.ShareHelper

/**
 * Data class representing a post in the feed.
 *
 * @param id Unique identifier for the post
 * @param userName Name of the user who posted
 * @param userAvatarUrl URL of the user's avatar
 * @param timestamp When the post was created
 * @param content Main text content of the post
 * @param likeCount Number of likes
 * @param commentCount Number of comments
 * @param isLiked Whether current user liked this post
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
 * Displays stories row, quick access tiles, and a scrollable feed of posts.
 *
 * @param posts List of posts to display in the feed
 * @param stories List of stories to display
 * @param quickAccessItems List of quick access tiles
 * @param onPostLikeClick Callback when a post is liked
 * @param onPostCommentClick Callback when comment button is clicked
 * @param onPostShareClick Callback when share button is clicked
 * @param onPostMoreClick Callback when more options is clicked
 * @param onStoryClick Callback when a story is clicked
 * @param onAddStoryClick Callback when "Add Story" is clicked
 * @param onQuickAccessClick Callback when a quick access tile is clicked
 * @param onSearchClick Callback when search field is clicked
 * @param onNotificationClick Callback when notification bell is clicked
 * @param onSettingsClick Callback when settings is clicked
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
    
    // State for real posts from mock data
    var posts by remember { mutableStateOf(MockPosts.posts) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top section with search bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimensions.Spacing.md)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimensions.Spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "UniVibe",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = { navigator.push(NotificationsScreen) }) {
                        Text("🔔")
                    }
                }

                UniVibeTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search posts, people...",
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                )
            }
        }

        // Stories row - using real stories from mock data
        item {
            StoriesRow(
                storyGroups = MockStories.storyGroups,
                onStoryClick = { storyGroup ->
                    val startIndex = MockStories.storyGroups.indexOf(storyGroup)
                    navigator.push(StoryViewerScreen(MockStories.storyGroups, startIndex))
                },
                onAddStoryClick = { /* TODO: Add story */ }
            )
        }

        // Quick access grid
        if (quickAccessItems.isNotEmpty()) {
            item {
                QuickAccessGrid(
                    items = quickAccessItems,
                    onTileClick = { title ->
                        when (title) {
                            "study_sessions" -> navigator.push(StudySessionsScreen)
                            "find_buddy" -> navigator.push(SearchScreen)
                            "class_notes" -> navigator.push(SearchScreen)
                            "campus_events" -> navigator.push(EventsScreen)
                            else -> {}
                        }
                    }
                )
            }

            // Divider
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = Dimensions.Spacing.md)
                )
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }
        }

        // Campus Features Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                Text(
                    "Campus Life",
                    style = MaterialTheme.typography.titleMedium
                )
                
                // First row of feature cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    FeatureCard(
                        emoji = "📅",
                        title = "Events",
                        onClick = { navigator.push(EventsScreen) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCard(
                        emoji = "👥",
                        title = "Clubs",
                        onClick = { navigator.push(ClubsScreen) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCard(
                        emoji = "📚",
                        title = "Study",
                        onClick = { navigator.push(StudySessionsScreen) },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                // Second row of feature cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    FeatureCard(
                        emoji = "🏢",
                        title = "Departments",
                        onClick = { navigator.push(DepartmentsScreen) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCard(
                        emoji = "🛍️",
                        title = "Marketplace",
                        onClick = { navigator.push(MarketplaceScreen) },
                        modifier = Modifier.weight(1f)
                    )
                    FeatureCard(
                        emoji = "💼",
                        title = "Jobs",
                        onClick = { navigator.push(JobsScreen) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Divider before feed
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
        }

        // Feed label
        item {
            Text(
                text = "What's New",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    start = Dimensions.Spacing.md,
                    end = Dimensions.Spacing.md,
                    bottom = Dimensions.Spacing.sm
                )
            )
        }

        // Posts feed - using real posts from mock data
        items(
            items = posts,
            key = { post -> post.id }
        ) { post ->
            PostCard(
                post = post,
                onLikeClick = { clickedPost ->
                    // Toggle like
                    posts = posts.map {
                        if (it.id == clickedPost.id) {
                            it.copy(
                                isLiked = !it.isLiked,
                                likeCount = if (it.isLiked) it.likeCount - 1 else it.likeCount + 1
                            )
                        } else it
                    }
                },
                onCommentClick = { clickedPost ->
                    navigator.push(PostDetailScreen(clickedPost.id))
                },
                onShareClick = { sharedPost ->
                    val shareText = ShareHelper.sharePost(sharedPost)
                    println("Share: $shareText")
                },
                onPostClick = { /* TODO: Post detail */ },
                onUserClick = { post ->
                    navigator.push(UserProfileScreen(post.authorId))
                }
            )
        }

        // Bottom message
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.lg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You're all caught up! 🎉",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
 * Convenience function to create sample/mock posts for preview and testing.
 */
fun getSamplePosts(): List<PostItem> = listOf(
    PostItem(
        id = "post_1",
        userName = "Sarah Chen",
        timestamp = "2 hours ago",
        content = "Just finished a breakthrough study session with the organic chemistry group! So grateful for this community. Anyone else struggling with mechanisms? Let's study together! 🔬📚",
        likeCount = 24,
        commentCount = 5,
        isLiked = false
    ),
    PostItem(
        id = "post_2",
        userName = "Alex Rivera",
        timestamp = "4 hours ago",
        content = "Library night everyone! Meeting at floor 3 study area at 7pm. Bring your laptops and your questions. Coffee is on me! ☕",
        likeCount = 18,
        commentCount = 8,
        isLiked = false
    ),
    PostItem(
        id = "post_3",
        userName = "Jordan Park",
        timestamp = "6 hours ago",
        content = "Pro tip: Use the Pomodoro technique (25 min focus, 5 min break) for studying. I went from C's to A's with this method! What's your studying hack?",
        likeCount = 47,
        commentCount = 12,
        isLiked = true
    ),
    PostItem(
        id = "post_4",
        userName = "Emma Davis",
        timestamp = "8 hours ago",
        content = "Campus rec is having a volleyball tournament next weekend! Looking for teammates. No experience necessary, just come for fun! ⚽",
        likeCount = 15,
        commentCount = 7,
        isLiked = false
    )
)

/**
 * Convenience function to create sample/mock stories for preview and testing.
 */
fun getSampleStories(): List<StoryItem> = listOf(
    StoryItem(
        id = "story_1",
        userName = "Maya Patel",
        isViewed = false
    ),
    StoryItem(
        id = "story_2",
        userName = "Chris Lee",
        isViewed = true
    ),
    StoryItem(
        id = "story_3",
        userName = "Taylor White",
        isViewed = false
    ),
    StoryItem(
        id = "story_4",
        userName = "Jamie Brown",
        isViewed = true
    ),
    StoryItem(
        id = "story_5",
        userName = "Morgan Black",
        isViewed = false
    )
)

/**
 * Feature card for displaying quick access to campus features.
 * Shows an emoji and title with a clickable card layout.
 */
@Composable
private fun FeatureCard(
    emoji: String,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}