package com.example.univibe.ui.screens.features

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.domain.models.StoryGroup
import com.example.univibe.ui.theme.Dimensions
import kotlinx.coroutines.delay

data class StoryViewerScreen(
    val storyGroups: List<StoryGroup>,
    val startIndex: Int = 0
) : Screen {
    @Composable
    override fun Content() {
        StoryViewerContent(storyGroups, startIndex)
    }
}

@Composable
private fun StoryViewerContent(
    storyGroups: List<StoryGroup>,
    startIndex: Int
) {
    val navigator = LocalNavigator.currentOrThrow
    var currentGroupIndex by remember { mutableStateOf(startIndex) }
    var currentStoryIndex by remember { mutableStateOf(0) }
    var isPaused by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    
    val currentGroup = storyGroups.getOrNull(currentGroupIndex)
    val currentStory = currentGroup?.stories?.getOrNull(currentStoryIndex)
    
    // Auto-advance timer
    LaunchedEffect(currentGroupIndex, currentStoryIndex, isPaused) {
        if (!isPaused && currentStory != null) {
            while (progress < 1f) {
                delay(50) // Update every 50ms
                progress += 0.05f / 3 // 3 seconds per story
                
                if (progress >= 1f) {
                    // Move to next story or group
                    val stories = currentGroup?.stories ?: return@LaunchedEffect
                    
                    if (currentStoryIndex < stories.size - 1) {
                        // Next story in current group
                        currentStoryIndex++
                        progress = 0f
                    } else if (currentGroupIndex < storyGroups.size - 1) {
                        // Next group
                        currentGroupIndex++
                        currentStoryIndex = 0
                        progress = 0f
                    } else {
                        // End of all stories
                        navigator.pop()
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
                        val width = size.width
                        if (offset.x < width / 2) {
                            // Tapped left side - previous story
                            if (currentStoryIndex > 0) {
                                currentStoryIndex--
                                progress = 0f
                            } else if (currentGroupIndex > 0) {
                                currentGroupIndex--
                                currentStoryIndex = storyGroups[currentGroupIndex].stories.size - 1
                                progress = 0f
                            }
                        } else {
                            // Tapped right side - next story
                            val stories = currentGroup?.stories ?: return@detectTapGestures
                            
                            if (currentStoryIndex < stories.size - 1) {
                                currentStoryIndex++
                                progress = 0f
                            } else if (currentGroupIndex < storyGroups.size - 1) {
                                currentGroupIndex++
                                currentStoryIndex = 0
                                progress = 0f
                            } else {
                                navigator.pop()
                            }
                        }
                        isPaused = false
                    },
                    onLongPress = {
                        isPaused = true
                    },
                    onPress = {
                        awaitRelease()
                        isPaused = false
                    }
                )
            }
    ) {
        // Story image placeholder
        if (currentStory != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            "Story from ${currentGroup?.user?.fullName}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        // Gradient overlay at top for better text visibility
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Transparent
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimensions.Spacing.default)
        ) {
            // Progress indicators
            if (currentGroup != null) {
                StoryProgressIndicators(
                    storyCount = currentGroup.stories.size,
                    currentIndex = currentStoryIndex,
                    progress = progress
                )
            }
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.default))
            
            // Header: User info
            if (currentGroup != null) {
                StoryHeader(
                    user = currentGroup.user,
                    onCloseClick = { navigator.pop() }
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Reply input at bottom
            StoryReplyInput()
        }
    }
}

@Composable
private fun StoryProgressIndicators(
    storyCount: Int,
    currentIndex: Int,
    progress: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(storyCount) { index ->
            val indicatorProgress = when {
                index < currentIndex -> 1f
                index == currentIndex -> progress
                else -> 0f
            }
            
            LinearProgressIndicator(
                progress = { indicatorProgress },
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .clip(MaterialTheme.shapes.extraSmall),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
private fun StoryHeader(
    user: com.example.univibe.domain.models.User,
    onCloseClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                modifier = Modifier
                    .size(Dimensions.AvatarSize.small)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.fullName.firstOrNull()?.uppercase() ?: "U",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
            
            Column {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "2h ago", // TODO: Calculate actual time
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)) {
            IconButton(onClick = { /* TODO: Show options */ }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Options",
                    tint = Color.White
                )
            }
            
            IconButton(onClick = onCloseClick) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun StoryReplyInput() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.White.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Dimensions.Spacing.default, vertical = Dimensions.Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Send message...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like",
                tint = Color.White,
                modifier = Modifier.size(Dimensions.IconSize.medium)
            )
        }
    }
}