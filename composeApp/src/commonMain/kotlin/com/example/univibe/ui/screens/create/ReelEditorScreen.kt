package com.example.univibe.ui.screens.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.example.univibe.domain.models.*
import com.example.univibe.ui.theme.BurgundyPrimary
import com.example.univibe.ui.theme.GoldAccent
import com.example.univibe.ui.theme.TealAccent

data class ReelEditorScreen(
    val reelDraft: ReelDraft
) : Screen {
    @Composable
    override fun Content() {
        ReelEditorContent(reelDraft)
    }
}

enum class ReelEditorTool {
    NONE, TRIM, TEXT, MUSIC, SPEED, EFFECTS, STICKERS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReelEditorContent(initialDraft: ReelDraft) {
    val navigator = LocalNavigator.currentOrThrow
    var draft by remember { mutableStateOf(initialDraft) }
    var selectedTool by remember { mutableStateOf(ReelEditorTool.NONE) }
    var showTextDialog by remember { mutableStateOf(false) }
    var showPreview by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Reel") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.Close, "Close")
                    }
                },
                actions = {
                    Button(
                        onClick = { navigator.popUntil { it != this } },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Post")
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
            // Video Preview (60% height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .background(Color.DarkGray)
            ) {
                // Video Thumbnail
                AsyncImage(
                    model = draft.thumbnailUrl,
                    contentDescription = "Video preview",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Overlay Elements
                if (draft.textOverlays.isNotEmpty()) {
                    draft.textOverlays.forEach { textOverlay ->
                        ReelTextElement(textOverlay)
                    }
                }
                
                if (draft.stickerElements.isNotEmpty()) {
                    draft.stickerElements.forEach { sticker ->
                        ReelStickerElement(sticker)
                    }
                }
            }
            
            // Tool Tabs (40% height with scrollable content)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
                    .background(Color.Black)
                    .border(1.dp, Color.DarkGray)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Tab buttons
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(ReelEditorTool.values().filter { it != ReelEditorTool.NONE }) { tool ->
                            FilterChip(
                                selected = selectedTool == tool,
                                onClick = { selectedTool = tool },
                                label = { Text(tool.name) },
                                modifier = Modifier.height(32.dp)
                            )
                        }
                    }
                    
                    // Tab Content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        when (selectedTool) {
                            ReelEditorTool.TRIM -> TrimToolPanel(draft) { newDraft ->
                                draft = newDraft
                            }
                            ReelEditorTool.TEXT -> TextToolPanel(
                                draft = draft,
                                onAddText = { showTextDialog = true },
                                onUpdateDraft = { newDraft -> draft = newDraft }
                            )
                            ReelEditorTool.MUSIC -> MusicToolPanel(draft) { newDraft ->
                                draft = newDraft
                            }
                            ReelEditorTool.SPEED -> SpeedToolPanel(draft) { newDraft ->
                                draft = newDraft
                            }
                            ReelEditorTool.EFFECTS -> EffectsToolPanel(draft) { newDraft ->
                                draft = newDraft
                            }
                            ReelEditorTool.STICKERS -> StickersToolPanel(draft) { newDraft ->
                                draft = newDraft
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
    
    // Text Input Dialog
    if (showTextDialog) {
        TextInputDialog(
            onDismiss = { showTextDialog = false },
            onAddText = { text, style, color ->
                val newTextOverlay = ReelTextOverlay(
                    id = "text_${System.currentTimeMillis()}",
                    text = text,
                    style = style,
                    color = color
                )
                draft = draft.copy(
                    textOverlays = draft.textOverlays + newTextOverlay
                )
                showTextDialog = false
            }
        )
    }
}

@Composable
private fun TrimToolPanel(
    draft: ReelDraft,
    onUpdateDraft: (ReelDraft) -> Unit
) {
    var trimStart by remember { mutableStateOf(draft.trimStart) }
    var trimEnd by remember { mutableStateOf(draft.trimEnd) }
    
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Trim Video", style = MaterialTheme.typography.titleSmall, color = Color.White)
        
        Text("Start: ${formatTime(trimStart.toInt())}s", color = Color.White.copy(alpha = 0.7f))
        Slider(
            value = trimStart,
            onValueChange = { trimStart = it },
            valueRange = 0f..draft.videoDuration.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text("End: ${formatTime(trimEnd.toInt())}s", color = Color.White.copy(alpha = 0.7f))
        Slider(
            value = trimEnd,
            onValueChange = { trimEnd = it },
            valueRange = trimStart..draft.videoDuration.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick = {
                onUpdateDraft(draft.copy(trimStart = trimStart, trimEnd = trimEnd))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Trim")
        }
    }
}

@Composable
private fun TextToolPanel(
    draft: ReelDraft,
    onAddText: () -> Unit,
    onUpdateDraft: (ReelDraft) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Add Text", style = MaterialTheme.typography.titleSmall, color = Color.White)
        
        Button(
            onClick = onAddText,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BurgundyPrimary)
        ) {
            Icon(Icons.Default.Add, null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add Text Overlay")
        }
        
        if (draft.textOverlays.isNotEmpty()) {
            Text("Text Overlays (${draft.textOverlays.size})", color = Color.White.copy(alpha = 0.7f))
            draft.textOverlays.forEach { overlay ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(Color.DarkGray, RoundedCornerShape(4.dp))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(overlay.text, color = Color.White, modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            onUpdateDraft(
                                draft.copy(
                                    textOverlays = draft.textOverlays.filter { it.id != overlay.id }
                                )
                            )
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Delete, null, tint = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
private fun MusicToolPanel(
    draft: ReelDraft,
    onUpdateDraft: (ReelDraft) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Add Music", style = MaterialTheme.typography.titleSmall, color = Color.White)
        
        if (draft.selectedMusic != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(GoldAccent.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(draft.selectedMusic!!.name, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(draft.selectedMusic!!.artist, color = Color.White.copy(alpha = 0.7f))
                }
                Button(
                    onClick = { onUpdateDraft(draft.copy(selectedMusic = null)) },
                    modifier = Modifier.size(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Icon(Icons.Default.Close, null, tint = Color.White)
                }
            }
        } else {
            LazyColumn {
                items(DEFAULT_MUSIC_TRACKS) { track ->
                    MusicTrackItem(track) {
                        onUpdateDraft(draft.copy(selectedMusic = track))
                    }
                }
            }
        }
    }
}

@Composable
private fun MusicTrackItem(track: MusicTrack, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(Icons.Default.MusicNote, null, tint = BurgundyPrimary, modifier = Modifier.size(24.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(track.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text("${track.artist} • ${formatTime(track.duration)}", color = Color.White.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun SpeedToolPanel(
    draft: ReelDraft,
    onUpdateDraft: (ReelDraft) -> Unit
) {
    val speeds = listOf(0.5f, 0.75f, 1f, 1.25f, 1.5f, 2f)
    
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Video Speed", style = MaterialTheme.typography.titleSmall, color = Color.White)
        Text("Current: ${draft.selectedSpeed}x", color = Color.White.copy(alpha = 0.7f))
        
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            speeds.forEach { speed ->
                FilterChip(
                    selected = draft.selectedSpeed == speed,
                    onClick = { onUpdateDraft(draft.copy(selectedSpeed = speed)) },
                    label = { Text("${speed}x") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun EffectsToolPanel(
    draft: ReelDraft,
    onUpdateDraft: (ReelDraft) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Filters & Adjustments", style = MaterialTheme.typography.titleSmall, color = Color.White)
        
        // Filter Selection
        Text("Filter", color = Color.White, fontWeight = FontWeight.SemiBold)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(ReelFilter.values()) { filter ->
                FilterChip(
                    selected = draft.selectedFilter == filter,
                    onClick = { onUpdateDraft(draft.copy(selectedFilter = filter)) },
                    label = { Text(filter.name) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Brightness
        Text("Brightness: ${draft.brightness}", color = Color.White.copy(alpha = 0.7f))
        Slider(
            value = draft.brightness.toFloat(),
            onValueChange = {
                onUpdateDraft(draft.copy(brightness = it.toInt()))
            },
            valueRange = -100f..100f,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Contrast
        Text("Contrast: ${draft.contrast}", color = Color.White.copy(alpha = 0.7f))
        Slider(
            value = draft.contrast.toFloat(),
            onValueChange = {
                onUpdateDraft(draft.copy(contrast = it.toInt()))
            },
            valueRange = -100f..100f,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Saturation
        Text("Saturation: ${draft.saturation}", color = Color.White.copy(alpha = 0.7f))
        Slider(
            value = draft.saturation.toFloat(),
            onValueChange = {
                onUpdateDraft(draft.copy(saturation = it.toInt()))
            },
            valueRange = -100f..100f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun StickersToolPanel(
    draft: ReelDraft,
    onUpdateDraft: (ReelDraft) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Add Stickers", style = MaterialTheme.typography.titleSmall, color = Color.White)
        
        StickerCategoriesReel.all.forEach { (category, stickers) ->
            Text(category, color = Color.White.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(stickers) { emoji ->
                    Surface(
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                val newSticker = ReelStickerElement(
                                    id = "sticker_${System.currentTimeMillis()}",
                                    emoji = emoji
                                )
                                onUpdateDraft(
                                    draft.copy(
                                        stickerElements = draft.stickerElements + newSticker
                                    )
                                )
                            },
                        shape = RoundedCornerShape(8.dp),
                        color = Color.DarkGray
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(emoji, style = MaterialTheme.typography.headlineSmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ReelTextElement(textOverlay: ReelTextOverlay) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = textOverlay.text,
            color = Color(android.graphics.Color.parseColor(textOverlay.color)),
            fontSize = MaterialTheme.typography.displaySmall.fontSize,
            fontWeight = when (textOverlay.style) {
                TextStyle.BOLD -> FontWeight.Bold
                else -> FontWeight.Normal
            }
        )
    }
}

@Composable
private fun ReelStickerElement(sticker: ReelStickerElement) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sticker.emoji,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )
    }
}

@Composable
private fun TextInputDialog(
    onDismiss: () -> Unit,
    onAddText: (String, TextStyle, String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var selectedStyle by remember { mutableStateOf(TextStyle.MODERN) }
    var selectedColor by remember { mutableStateOf("#FFFFFF") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Text") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Text") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text("Style", fontWeight = FontWeight.Bold)
                TextStyle.values().forEach { style ->
                    FilterChip(
                        selected = selectedStyle == style,
                        onClick = { selectedStyle = style },
                        label = { Text(style.name) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onAddText(text, selectedStyle, selectedColor) }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return if (mins > 0) {
        String.format("%d:%02d", mins, secs)
    } else {
        String.format("0:%02d", secs)
    }
}
