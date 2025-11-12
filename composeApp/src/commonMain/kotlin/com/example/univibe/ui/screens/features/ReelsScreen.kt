package com.example.univibe.ui.screens.features

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.univibe.domain.models.Reel
import com.example.univibe.data.mock.MockReels
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.BrandColors
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.ui.screens.detail.UserProfileScreen

object ReelsScreen : Screen {
    @Composable
    override fun Content() {
        ReelsScreenContent()
    }
}

enum class ReelsTab {
    FOR_YOU, FOLLOWING
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ReelsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    var selectedTab by remember { mutableStateOf(ReelsTab.FOR_YOU) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    val reels = remember { MockReels.reels }

    val filteredReels = remember(selectedTab) {
        when (selectedTab) {
            ReelsTab.FOR_YOU -> reels
            ReelsTab.FOLLOWING -> reels.filter { it.isFollowing }
        }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { filteredReels.size }
    )

    // Track which reel is active for auto-play/pause
    var activePage by remember { mutableStateOf(0) }
    LaunchedEffect(pagerState.currentPage) {
        activePage = pagerState.currentPage
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Vertical Video Feed
        if (filteredReels.isNotEmpty()) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = true
            ) { page ->
                val reel = filteredReels[page]
                ReelPlayer(
                    reel = reel,
                    isActive = page == activePage,
                    onProfileClick = {
                        navigator.push(UserProfileScreen(reel.creatorId))
                    }
                )
            }
        } else {
            // Empty State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(
                        PlatformIcons.PhotoLibrary,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = if (selectedTab == ReelsTab.FOLLOWING) {
                            "No reels from people you follow"
                        } else {
                            "No reels available"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }

        // Top Bar with Tab Selector
        TopAppBar(
            title = {
                TabRow(
                    selectedTabIndex = selectedTab.ordinal,
                    modifier = Modifier.width(200.dp),
                    containerColor = Color.Transparent,
                    indicator = { },
                    divider = { }
                ) {
                    ReelsTab.values().forEach { tab ->
                        Tab(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            text = {
                                Text(
                                    text = tab.name.replace("_", " "),
                                    color = if (selectedTab == tab) Color.White else Color.White.copy(alpha = 0.6f),
                                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { showSearch = !showSearch }) {
                    Icon(
                        PlatformIcons.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.5f),
                        Color.Transparent
                    )
                )
            )
        )
    }
}

@Composable
private fun ReelPlayer(
    reel: Reel,
    isActive: Boolean,
    onProfileClick: () -> Unit
) {
    var isPlaying by remember { mutableStateOf(isActive) }
    var isMuted by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(reel.isLiked) }
    var likeCount by remember { mutableStateOf(reel.likeCount) }
    var isSaved by remember { mutableStateOf(reel.isSaved) }
    var showComments by remember { mutableStateOf(false) }

    LaunchedEffect(isActive) {
        isPlaying = isActive
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        isPlaying = !isPlaying
                    },
                    onDoubleTap = {
                        if (!isLiked) {
                            isLiked = true
                            likeCount++
                        }
                    }
                )
            }
    ) {
        // Video Thumbnail (placeholder for actual video)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        )

        // Dark overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )

        // Play/Pause Indicator (center)
        if (!isPlaying) {
            Icon(
                imageVector = PlatformIcons.PlayCircleFilled,
                contentDescription = "Play",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center),
                tint = Color.White.copy(alpha = 0.8f)
            )
        }

        // Mute Button (top right)
        IconButton(
            onClick = { isMuted = !isMuted },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = PlatformIcons.MusicNote,
                contentDescription = if (isMuted) "Unmute" else "Mute",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        // Bottom Gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        // Left Side Content (Creator info & Caption)
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 80.dp, end = 60.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Creator Avatar & Name with Follow Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onProfileClick() }
            ) {
                UserAvatar(
                    imageUrl = reel.creatorAvatarUrl,
                    name = reel.creatorName,
                    size = 44.dp,
                    onClick = onProfileClick
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = reel.creatorName,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "@${reel.creatorUsername}",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Caption
            Text(
                text = reel.caption,
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            // Tags
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                reel.tags.take(3).forEach { tag ->
                    Text(
                        text = tag,
                        color = Color.Cyan,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable { /* Open tag view */ }
                    )
                }
            }

            // Audio Info
            if (reel.audioTitle != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        PlatformIcons.MusicNote,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = reel.audioTitle,
                        color = Color.White,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Right Side Action Buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Like Button
            ReelActionButton(
                icon = if (isLiked) PlatformIcons.Favorite else PlatformIcons.FavoriteBorder,
                count = likeCount,
                tint = if (isLiked) Color.Red else Color.White,
                onClick = {
                    isLiked = !isLiked
                    likeCount += if (isLiked) 1 else -1
                }
            )

            // Comment Button
            ReelActionButton(
                icon = PlatformIcons.ChatBubble,
                count = reel.commentCount,
                onClick = { showComments = !showComments }
            )

            // Share Button
            ReelActionButton(
                icon = PlatformIcons.Share,
                count = reel.shareCount,
                onClick = { /* Share reel */ }
            )

            // Save Button
            ReelActionButton(
                icon = if (isSaved) PlatformIcons.BookmarkFilled else PlatformIcons.BookmarkBorder,
                count = 0,
                tint = if (isSaved) Color.Yellow else Color.White,
                showCount = false,
                onClick = { isSaved = !isSaved }
            )

            // More Options
            IconButton(
                onClick = { /* More options */ },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    PlatformIcons.MoreVert,
                    contentDescription = "More",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Video Progress Bar (optional)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.White.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.35f) // Simulated progress
                    .background(Color.Red)
            )
        }
    }
}

@Composable
private fun ReelActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: Int,
    tint: Color = Color.White,
    showCount: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(32.dp)
        )
        if (showCount && count > 0) {
            Text(
                text = when {
                    count >= 1_000_000 -> "${(count / 1_000_000)}M"
                    count >= 1_000 -> "${(count / 1_000)}K"
                    else -> count.toString()
                },
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// Import this if it's not already available
@androidx.compose.foundation.ExperimentalFoundationApi
@Composable
fun rememberScrollState(): androidx.compose.foundation.ScrollState {
    return androidx.compose.foundation.rememberScrollState()
}
