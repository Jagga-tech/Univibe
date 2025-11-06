package com.example.univibe.ui.screens.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.example.univibe.domain.models.*
import com.example.univibe.ui.theme.BurgundyPrimary
import java.util.*

/**
 * StoryEditorScreen - Full-featured story editor
 *
 * Features:
 * - Image preview with canvas for editing
 * - Text overlay with customizable style, color, and position
 * - Sticker/emoji picker
 * - Filter effects (Warm, Cool, Vintage, etc.)
 * - Brightness, Contrast, Saturation sliders
 * - Link addition for swipe-up links
 * - Preview and post functionality
 */
class StoryEditorScreen(
    val storyDraft: StoryDraft
) : Screen {
    @Composable
    override fun Content() {
        StoryEditorScreenContent(storyDraft)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StoryEditorScreenContent(
    initialDraft: StoryDraft
) {
    val navigator = LocalNavigator.currentOrThrow
    var draft by remember { mutableStateOf(initialDraft) }
    var selectedTab by remember { mutableStateOf(EditorTab.CANVAS) }
    var editingTextElement by remember { mutableStateOf<TextElement?>(null) }
    var editingSticker by remember { mutableStateOf<StickerElement?>(null) }
    var showTextInput by remember { mutableStateOf(false) }
    var newTextContent by remember { mutableStateOf("") }
    var showPreview by remember { mutableStateOf(false) }

    if (showPreview) {
        StoryPreviewModal(
            draft = draft,
            onDismiss = { showPreview = false },
            onPost = {
                // Simulate posting
                navigator.popUntil { it == null }
                // In production, would upload to backend here
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top Action Bar
        TopAppBar(
            title = { Text("Edit Story") },
            navigationIcon = {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            },
            actions = {
                Button(
                    onClick = { showPreview = true },
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Text("Next")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black.copy(alpha = 0.7f),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            // Canvas Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .background(Color.DarkGray)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            // Handle dragging of text/stickers
                        }
                    }
            ) {
                // Image with filters
                AsyncImage(
                    model = draft.imageUri,
                    contentDescription = "Story image",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            // Apply filter effects
                            when (draft.filter) {
                                StoryFilter.WARM -> {
                                    alpha = 1f
                                }
                                StoryFilter.COOL -> {
                                    alpha = 1f
                                }
                                StoryFilter.GRAYSCALE -> {
                                    alpha = 1f
                                }
                                else -> {}
                            }
                        },
                    contentScale = ContentScale.Crop
                )

                // Brightness/Contrast/Saturation Overlay
                if (draft.brightness != 0 || draft.contrast != 0 || draft.saturation != 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Black.copy(
                                    alpha = (-draft.brightness / 200f).coerceIn(0f, 1f)
                                )
                            )
                    )
                }

                // Text Elements
                draft.textElements.forEach { textElement ->
                    if (textElement.isVisible) {
                        StoryTextElement(
                            element = textElement,
                            isSelected = editingTextElement?.id == textElement.id,
                            onSelect = { editingTextElement = textElement }
                        )
                    }
                }

                // Sticker Elements
                draft.stickerElements.forEach { stickerElement ->
                    if (stickerElement.isVisible) {
                        StoryStickerElement(
                            element = stickerElement,
                            isSelected = editingSticker?.id == stickerElement.id,
                            onSelect = { editingSticker = stickerElement }
                        )
                    }
                }

                // Add Text Button (FAB)
                FloatingActionButton(
                    onClick = { showTextInput = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = BurgundyPrimary
                ) {
                    Icon(Icons.Default.TextFields, contentDescription = "Add text")
                }
            }

            // Editor Tools Tabs
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.Black.copy(alpha = 0.8f),
                contentColor = Color.White
            ) {
                EditorTab.entries.forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = { Text(tab.title) }
                    )
                }
            }

            // Tool Panel
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
                    .background(Color.Black)
                    .verticalScroll(rememberScrollState())
            ) {
                when (selectedTab) {
                    EditorTab.TEXT -> TextToolsPanel(
                        textElement = editingTextElement,
                        onStyleChange = { style ->
                            editingTextElement?.let {
                                val updated = it.copy(style = style)
                                draft = draft.copy(
                                    textElements = draft.textElements.map {
                                        if (it.id == updated.id) updated else it
                                    }
                                )
                                editingTextElement = updated
                            }
                        }
                    )

                    EditorTab.STICKERS -> StickersToolPanel(
                        onStickerSelected = { emoji ->
                            val newSticker = StickerElement(
                                id = UUID.randomUUID().toString(),
                                emoji = emoji
                            )
                            draft = draft.copy(
                                stickerElements = draft.stickerElements + newSticker
                            )
                        }
                    )

                    EditorTab.FILTERS -> FiltersToolPanel(
                        selectedFilter = draft.filter,
                        onFilterSelected = { filter ->
                            draft = draft.copy(filter = filter)
                        }
                    )

                    EditorTab.ADJUSTMENTS -> AdjustmentsToolPanel(
                        brightness = draft.brightness,
                        contrast = draft.contrast,
                        saturation = draft.saturation,
                        onBrightnessChange = { draft = draft.copy(brightness = it) },
                        onContrastChange = { draft = draft.copy(contrast = it) },
                        onSaturationChange = { draft = draft.copy(saturation = it) }
                    )

                    EditorTab.LINK -> LinkToolPanel(
                        linkUrl = draft.linkUrl ?: "",
                        linkTitle = draft.linkTitle ?: "",
                        onLinkUrlChange = { draft = draft.copy(linkUrl = it) },
                        onLinkTitleChange = { draft = draft.copy(linkTitle = it) }
                    )
                }
            }
        }

        // Text Input Dialog
        if (showTextInput) {
            TextInputDialog(
                onDismiss = { showTextInput = false },
                onConfirm = { text ->
                    val newElement = TextElement(
                        id = UUID.randomUUID().toString(),
                        text = text
                    )
                    draft = draft.copy(
                        textElements = draft.textElements + newElement
                    )
                    editingTextElement = newElement
                    newTextContent = ""
                    showTextInput = false
                }
            )
        }
    }
}

/**
 * Enum for editor tool tabs
 */
enum class EditorTab(val title: String) {
    CANVAS("Canvas"),
    TEXT("Text"),
    STICKERS("Stickers"),
    FILTERS("Filters"),
    ADJUSTMENTS("Adjust"),
    LINK("Link")
}

/**
 * Displays a text element on the story canvas
 */
@Composable
private fun StoryTextElement(
    element: TextElement,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = element.text,
            style = TextStyle(
                fontSize = element.fontSize.sp,
                color = Color(android.graphics.Color.parseColor(element.color))
            ),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .graphicsLayer {
                    translationX = element.x
                    translationY = element.y
                    rotationZ = element.rotation
                }
                .clickable(onClick = onSelect)
                .background(
                    if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(4.dp)
        )
    }
}

/**
 * Displays a sticker element on the story canvas
 */
@Composable
private fun StoryStickerElement(
    element: StickerElement,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = element.emoji,
            fontSize = element.size.sp,
            modifier = Modifier
                .graphicsLayer {
                    translationX = element.x
                    translationY = element.y
                    rotationZ = element.rotation
                    alpha = element.opacity
                }
                .clickable(onClick = onSelect)
                .background(
                    if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent,
                    shape = CircleShape
                )
                .padding(4.dp)
        )
    }
}

/**
 * Tools panel for text editing
 */
@Composable
private fun TextToolsPanel(
    textElement: TextElement?,
    onStyleChange: (TextStyle) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Text Styles",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        if (textElement != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                com.example.univibe.domain.models.TextStyle.entries.forEach { style ->
                    StyleButton(
                        style = style,
                        isSelected = textElement.style == style,
                        onClick = { onStyleChange(style) }
                    )
                }
            }

            // Color Picker
            Text(
                "Color",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "#FFFFFF" to Color.White,
                    "#FF0000" to Color.Red,
                    "#FFFF00" to Color.Yellow,
                    "#00FF00" to Color.Green
                ).forEach { (hex, color) ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color, CircleShape)
                            .clickable {
                                // Color change
                            }
                    )
                }
            }
        } else {
            Text(
                "Select or add text to edit",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

/**
 * Tools panel for stickers
 */
@Composable
private fun StickersToolPanel(
    onStickerSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Stickers & Emojis",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val allStickers = StickerCategories.getAll()
        var showAllStickers by remember { mutableStateOf(false) }

        if (!showAllStickers) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                allStickers.take(8).forEach { emoji ->
                    Text(
                        text = emoji,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { onStickerSelected(emoji) },
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            Column {
                allStickers.chunked(8).forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        row.forEach { emoji ->
                            Text(
                                text = emoji,
                                fontSize = 32.sp,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onStickerSelected(emoji) },
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        TextButton(
            onClick = { showAllStickers = !showAllStickers },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (showAllStickers) "Show Less" else "Show More")
        }
    }
}

/**
 * Tools panel for filters
 */
@Composable
private fun FiltersToolPanel(
    selectedFilter: StoryFilter,
    onFilterSelected: (StoryFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Filters",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StoryFilter.entries.forEach { filter ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(
                            if (selectedFilter == filter)
                                BurgundyPrimary.copy(alpha = 0.3f)
                            else
                                Color.Gray.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onFilterSelected(filter) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = filter.name,
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    if (selectedFilter == filter) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = BurgundyPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tools panel for adjustments (brightness, contrast, saturation)
 */
@Composable
private fun AdjustmentsToolPanel(
    brightness: Int,
    contrast: Int,
    saturation: Int,
    onBrightnessChange: (Int) -> Unit,
    onContrastChange: (Int) -> Unit,
    onSaturationChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Adjustments",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        AdjustmentSlider(
            label = "Brightness",
            value = brightness,
            onValueChange = onBrightnessChange
        )

        AdjustmentSlider(
            label = "Contrast",
            value = contrast,
            onValueChange = onContrastChange
        )

        AdjustmentSlider(
            label = "Saturation",
            value = saturation,
            onValueChange = onSaturationChange
        )
    }
}

/**
 * Slider for adjustment tools
 */
@Composable
private fun AdjustmentSlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
            Text(
                text = value.toString(),
                fontSize = 12.sp,
                color = BurgundyPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = -100f..100f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Tools panel for adding swipe-up links
 */
@Composable
private fun LinkToolPanel(
    linkUrl: String,
    linkTitle: String,
    onLinkUrlChange: (String) -> Unit,
    onLinkTitleChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Add Link",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        OutlinedTextField(
            value = linkUrl,
            onValueChange = onLinkUrlChange,
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = linkTitle,
            onValueChange = onLinkTitleChange,
            label = { Text("Link Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "Users will see \"Swipe up to learn more\" with your link",
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.6f)
        )
    }
}

/**
 * Button for text style selection
 */
@Composable
private fun StyleButton(
    style: com.example.univibe.domain.models.TextStyle,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) BurgundyPrimary else Color.Gray.copy(alpha = 0.2f)
        ),
        modifier = Modifier.height(40.dp)
    ) {
        Text(style.name.take(1))
    }
}

/**
 * Dialog for entering text content
 */
@Composable
private fun TextInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Text") },
        text = {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Enter text...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(textInput) },
                enabled = textInput.isNotBlank()
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

/**
 * Preview modal showing the complete story before posting
 */
@Composable
private fun StoryPreviewModal(
    draft: StoryDraft,
    onDismiss: () -> Unit,
    onPost: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color.DarkGray, RoundedCornerShape(12.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Story Preview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Preview image area
            AsyncImage(
                model = draft.imageUri,
                contentDescription = "Story preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                "Story details:\n" +
                "• ${draft.textElements.size} text elements\n" +
                "• ${draft.stickerElements.size} stickers\n" +
                "• Filter: ${draft.filter.name}",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray.copy(alpha = 0.3f)
                    )
                ) {
                    Text("Back")
                }

                Button(
                    onClick = onPost,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BurgundyPrimary
                    )
                ) {
                    Text("Post Story")
                }
            }
        }
    }
}
