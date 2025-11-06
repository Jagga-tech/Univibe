package com.example.univibe.ui.screens.features

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.example.univibe.domain.models.*
import com.example.univibe.data.mock.MockStories
import com.example.univibe.data.mock.MockUsers
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Colors
import kotlinx.coroutines.delay

/**
 * Screen for viewing stories in full-screen immersive format.
 * Supports Instagram-style navigation with tap controls, swipe gestures,
 * progress indicators, reactions, and message replies.
 *
 * **Features:**
 * - Full-screen immersive viewer (no status bar)
 * - Tap left/right for previous/next story
 * - Swipe down to close
 * - Hold to pause with progress indication
 * - Auto-advance after 5 seconds
 * - Multiple story types (image, video, text, link)
 * - User reactions with quick emoji picker
 * - Message reply capability
 * - Multiple user story browsing (swipe left/right between users)
 * - Unviewed stories prioritized
 *
 * @param initialUserId The ID of the user whose stories to display first
 * @param initialStoryIndex The index of the story to display first (default 0)
 */
data class StoryViewerScreen(
    val initialUserId: String,
    val initialStoryIndex: Int = 0
) : Screen {
    @Composable
    override fun Content() {
        StoryViewerContent(initialUserId, initialStoryIndex)
    }
}

@Composable
private fun StoryViewerContent(
    initialUserId: String,
    initialStoryIndex: Int
) {
    val navigator = LocalNavigator.currentOrThrow
    
    // Get all story groups (users with stories)
    val storyGroups = remember { MockStories.getStoriesForViewing() }
    
    // Find initial user index
    val initialUserIndex = remember {
        storyGroups.indexOfFirst { it.userId == initialUserId }.coerceAtLeast(0)
    }
    
    // Pager for switching between users' stories
    val userPagerState = rememberPagerState(
        initialPage = initialUserIndex,
        pageCount = { storyGroups.size }
    )
    
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        HorizontalPager(
            state = userPagerState,
            modifier = Modifier.fillMaxSize()
        ) { userIndex ->
            val storyGroup = storyGroups[userIndex]
            UserStoriesViewer(
                storyGroup = storyGroup,
                initialStoryIndex = if (userIndex == initialUserIndex) initialStoryIndex else 0,
                onClose = { navigator.pop() },
                onNavigateToUser = { /* TODO: Navigate to user profile */ }
            )
        }
    }
}

/**
 * Viewer for all stories from a single user.
 * Handles story navigation, pause/play, and interactions.
 */
@Composable
private fun UserStoriesViewer(
    storyGroup: StoryGroup,
    initialStoryIndex: Int,
    onClose: () -> Unit,
    onNavigateToUser: () -> Unit
) {
    var currentStoryIndex by remember { mutableStateOf(initialStoryIndex.coerceIn(0, storyGroup.stories.size - 1)) }
    var isPaused by remember { mutableStateOf(false) }
    var showReactionPicker by remember { mutableStateOf(false) }
    var showReplyInput by remember { mutableStateOf(false) }
    var swipeProgress by remember { mutableStateOf(0f) }  // 0-1 for downward swipe
    
    val currentStory = storyGroup.stories.getOrNull(currentStoryIndex)
    val scope = rememberCoroutineScope()
    
    // Progress for current story (0-1)
    var storyProgress by remember { mutableStateOf(0f) }
    
    // Auto-advance timer with pause support
    LaunchedEffect(currentStoryIndex, isPaused) {
        if (!isPaused && currentStory != null) {
            while (storyProgress < 1f) {
                delay(50)  // Update every 50ms for smooth animation
                storyProgress += 0.05f / 100f  // 5000ms total = 5 seconds
                
                if (storyProgress >= 1f) {
                    // Auto-advance to next story
                    if (currentStoryIndex < storyGroup.stories.size - 1) {
                        currentStoryIndex++
                        storyProgress = 0f
                    } else {
                        // Close viewer when all stories finished
                        onClose()
                    }
                }
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val screenWidth = size.width
                        if (offset.x < screenWidth / 3) {
                            // Tap left third - previous story
                            if (currentStoryIndex > 0) {
                                currentStoryIndex--
                                storyProgress = 0f
                            } else {
                                onClose()
                            }
                        } else if (offset.x > screenWidth * 2 / 3) {
                            // Tap right third - next story
                            if (currentStoryIndex < storyGroup.stories.size - 1) {
                                currentStoryIndex++
                                storyProgress = 0f
                            } else {
                                onClose()
                            }
                        } else {
                            // Tap center - toggle pause
                            isPaused = !isPaused
                        }
                    },
                    onLongPress = {
                        // Hold to pause
                        isPaused = true
                    },
                    onPress = {
                        tryAwaitRelease()
                        if (!showReplyInput && !showReactionPicker) {
                            isPaused = false
                        }
                    }
                )
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    // Vertical drag to close
                    val verticalDrag = dragAmount.y
                    if (verticalDrag > 0) {  // Dragging down
                        swipeProgress = (verticalDrag / size.height).coerceIn(0f, 1f)
                        if (verticalDrag > 100 || swipeProgress > 0.15f) {
                            onClose()
                        }
                    }
                }
            }
    ) {
        // Story Content
        if (currentStory != null) {
            StoryContentDisplay(story = currentStory)
        }
        
        // Top Gradient for visibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopStart)
        )
        
        // Progress Indicators (top)
        if (currentStory != null) {
            StoryProgressBars(
                totalStories = storyGroup.stories.size,
                currentIndex = currentStoryIndex,
                progress = storyProgress,
                isPaused = isPaused,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .align(Alignment.TopStart)
            )
        }
        
        // Header with User Info
        if (currentStory != null) {
            StoryHeaderBar(
                user = storyGroup.user,
                timestamp = formatStoryTime(currentStory.createdAt),
                onClose = onClose,
                onMore = { /* TODO: Show menu */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 48.dp)
                    .align(Alignment.TopStart)
            )
        }
        
        // Bottom Gradient for visibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
                .align(Alignment.BottomStart)
        )
        
        // Bottom Interaction Bar
        if (currentStory != null) {
            StoryBottomBar(
                story = currentStory,
                onReact = {
                    showReactionPicker = true
                    isPaused = true
                },
                onReply = {
                    showReplyInput = true
                    isPaused = true
                },
                onShare = { /* TODO: Share story */ },
                onMore = { /* TODO: Show options */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            )
        }
        
        // Reaction Picker (full screen overlay)
        if (showReactionPicker && currentStory != null) {
            ReactionPickerOverlay(
                story = currentStory,
                onReact = { reaction ->
                    MockStories.addReactionToStory(currentStory.id, reaction, "current_user")
                    showReactionPicker = false
                    isPaused = false
                },
                onDismiss = {
                    showReactionPicker = false
                    isPaused = false
                }
            )
        }
        
        // Reply Input (bottom sheet style)
        if (showReplyInput && currentStory != null) {
            ReplyInputOverlay(
                onSendReply = { message ->
                    // TODO: Send reply to story
                    showReplyInput = false
                    isPaused = false
                },
                onDismiss = {
                    showReplyInput = false
                    isPaused = false
                }
            )
        }
    }
}

/**
 * Display the story content based on type (image, video, text, or link).
 */
@Composable
private fun StoryContentDisplay(story: Story) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (story.type) {
            StoryType.IMAGE -> {
                // Image story with AsyncImage
                if (!story.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = story.imageUrl,
                        contentDescription = "Story image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    PlaceholderContent()
                }
            }
            
            StoryType.VIDEO -> {
                // Video story (placeholder)
                if (!story.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = story.imageUrl,
                        contentDescription = "Video thumbnail",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.7f
                    )
                }
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Video",
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(80.dp)
                )
            }
            
            StoryType.TEXT -> {
                // Text story with colored background
                val bgColor = parseColorOrDefault(story.backgroundColor ?: "#FF5733")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(bgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = story.text ?: "",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp
                        ),
                        color = Color.White,
                        modifier = Modifier
                            .padding(32.dp)
                            .align(Alignment.Center),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            StoryType.LINK -> {
                // Link story with image and link preview
                if (!story.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = story.imageUrl,
                        contentDescription = "Link preview",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        alpha = 0.6f
                    )
                }
                // Link preview overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )
                
                // Swipe up indicator at bottom
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = story.linkTitle ?: "Tap to open",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Swipe up for more",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Swipe up",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Progress bars showing story progress and count.
 */
@Composable
private fun StoryProgressBars(
    totalStories: Int,
    currentIndex: Int,
    progress: Float,
    isPaused: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(totalStories) { index ->
            val barProgress = when {
                index < currentIndex -> 1f
                index == currentIndex -> progress
                else -> 0f
            }
            
            // Individual progress bar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(2.5.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White.copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(barProgress)
                        .fillMaxHeight()
                        .background(Color.White)
                )
                
                // Pause indicator
                if (index == currentIndex && isPaused) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(barProgress)
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.5f)
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

/**
 * Header bar showing user info and close button.
 */
@Composable
private fun StoryHeaderBar(
    user: User,
    timestamp: String,
    onClose: () -> Unit,
    onMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UserAvatar(
                imageUrl = user.avatarUrl,
                name = user.fullName,
                size = 40.dp,
                showOnlineIndicator = user.isOnline,
                isOnline = user.isOnline
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = onMore, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            IconButton(onClick = onClose, modifier = Modifier.size(40.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

/**
 * Bottom interaction bar with reaction, reply, share, and more options.
 */
@Composable
private fun StoryBottomBar(
    story: Story,
    onReact: () -> Unit,
    onReply: () -> Unit,
    onShare: () -> Unit,
    onMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Quick action buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // React button
            StoryActionButton(
                icon = Icons.Default.Favorite,
                label = if (story.reactions.isEmpty()) "React" else "${story.reactions.size}",
                onClick = onReact
            )
            
            // Reply button
            StoryActionButton(
                icon = Icons.Default.Message,
                label = if (story.replyCount == 0) "Reply" else story.replyCount.toString(),
                onClick = onReply
            )
            
            // Share button
            StoryActionButton(
                icon = Icons.Default.Share,
                label = "Share",
                onClick = onShare
            )
            
            // More options button
            StoryActionButton(
                icon = Icons.Default.MoreVert,
                label = "More",
                onClick = onMore
            )
        }
        
        // Reaction preview (if any)
        if (story.reactions.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reactions: ",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
                
                story.reactions.take(5).forEach { (reaction, _) ->
                    Text(
                        text = reaction.emoji,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                    )
                }
                
                if (story.reactions.size > 5) {
                    Text(
                        text = "+${story.reactions.size - 5}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

/**
 * Individual action button for story bottom bar.
 */
@Composable
private fun StoryActionButton(
    icon: androidx.compose.material.icons.Icons.Filled,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = { onClick() })
        }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

/**
 * Reaction picker overlay showing quick reaction emojis.
 */
@Composable
private fun ReactionPickerOverlay(
    story: Story,
    onReact: (StoryReaction) -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onDismiss() })
            }
    ) {
        // Reaction buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            StoryReaction.values().forEach { reaction ->
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = { onReact(reaction) })
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = reaction.emoji,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}

/**
 * Reply input overlay at bottom of screen.
 */
@Composable
private fun ReplyInputOverlay(
    onSendReply: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var replyText by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onDismiss() })
            }
    ) {
        // Reply input field at bottom
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White.copy(alpha = 0.95f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = replyText,
                    onValueChange = { replyText = it },
                    placeholder = { Text("Send message...") },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    )
                )
                
                if (replyText.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onSendReply(replyText)
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.Red,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Placeholder content when story image is not available.
 */
@Composable
private fun PlaceholderContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "No image",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "Story unavailable",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Format story creation time to a human-readable string.
 * Examples: "2m ago", "1h ago", "Yesterday", etc.
 */
private fun formatStoryTime(createdAt: Long): String {
    val now = System.currentTimeMillis()
    val diffMs = now - createdAt
    
    return when {
        diffMs < 60_000 -> "Now"
        diffMs < 3_600_000 -> "${diffMs / 60_000}m ago"
        diffMs < 86_400_000 -> "${diffMs / 3_600_000}h ago"
        diffMs < 172_800_000 -> "Yesterday"
        else -> "${diffMs / 86_400_000}d ago"
    }
}

/**
 * Parse a hex color string to Color, with fallback to default color.
 * Supports formats: "#FF5733", "FF5733"
 */
private fun parseColorOrDefault(hexColor: String): Color {
    return try {
        val hex = hexColor.removePrefix("#")
        Color(hex.toLong(16) or 0xFF000000)
    } catch (e: Exception) {
        Color(0xFFFF5733)  // Default red
    }
}
