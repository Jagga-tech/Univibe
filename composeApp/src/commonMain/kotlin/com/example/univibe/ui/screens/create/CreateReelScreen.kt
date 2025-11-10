package com.example.univibe.ui.screens.create

import com.example.univibe.util.getCurrentTimeMillis
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.domain.models.ReelDraft
import com.example.univibe.ui.theme.BrandColors
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.ui.utils.formatDuration

data class VideoItem(
    val id: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val duration: Int // in seconds
)

object CreateReelScreen : Screen {
    @Composable
    override fun Content() {
        CreateReelScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateReelScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    var selectedVideoId by remember { mutableStateOf<String?>(null) }
    var showRecordDialog by remember { mutableStateOf(false) }
    
    // Mock video gallery
    val galleryVideos = remember {
        List(12) { index ->
            VideoItem(
                id = "video_$index",
                thumbnailUrl = "https://picsum.photos/seed/video$index/400/600",
                videoUrl = "https://example.com/video$index.mp4",
                duration = (15..60).random()
            )
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Reel") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(PlatformIcons.Close, "Close")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Record Video Option
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(16.dp)
                    .clickable { showRecordDialog = true },
                colors = CardDefaults.cardColors(
                    containerColor = BrandColors.Burgundy
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = PlatformIcons.Videocam,
                            contentDescription = "Record Video",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White
                        )
                        Text(
                            text = "Record Video",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "15-60 seconds",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            
            // Gallery Section Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Videos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            
            // Video Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(galleryVideos) { video ->
                    VideoThumbnailCard(
                        video = video,
                        isSelected = selectedVideoId == video.id,
                        onClick = {
                            selectedVideoId = video.id
                            val reelDraft = ReelDraft(
                                id = "reel_${getCurrentTimeMillis()}",
                                videoUrl = video.videoUrl,
                                thumbnailUrl = video.thumbnailUrl,
                                videoDuration = video.duration,
                                trimEnd = video.duration.toFloat()
                            )
                            navigator.push(ReelEditorScreen(reelDraft))
                        }
                    )
                }
            }
        }
    }
    
    // Record Dialog
    if (showRecordDialog) {
        AlertDialog(
            onDismissRequest = { showRecordDialog = false },
            title = { Text("Record Video") },
            text = { Text("Camera integration coming soon. For now, select from gallery.") },
            confirmButton = {
                Button(onClick = { showRecordDialog = false }) {
                    Text("Got it")
                }
            }
        )
    }
}

@Composable
private fun VideoThumbnailCard(
    video: VideoItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        // Thumbnail (placeholder - in production would use AsyncImage from Coil)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
        )
        
        // Dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )
        
        // Play icon
        Icon(
            imageVector = PlatformIcons.PlayCircleFilled,
            contentDescription = "Play",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp)
        )
        
        // Duration badge
        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp),
            color = Color.Black.copy(alpha = 0.7f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = formatDuration(video.duration),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
        
        // Selection indicator
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BrandColors.Burgundy.copy(alpha = 0.5f))
            ) {
                Icon(
                    PlatformIcons.CheckCircle,
                    contentDescription = "Selected",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
        }
    }
}
