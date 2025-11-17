package com.example.univibe.ui.screens.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.CreationScreen
import com.example.univibe.ui.templates.CreationScreenConfig
import com.example.univibe.ui.templates.CreationStep
import com.example.univibe.ui.components.UserAvatar

/**
 * Modern Create Post Screen using UniVibe Design System
 * Professional post creation interface with rich formatting and media support
 */
object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        CreatePostScreenContent()
    }
}

@Composable
private fun CreatePostScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var postContent by remember { mutableStateOf("") }
    var selectedVisibility by remember { mutableStateOf(PostVisibility.PUBLIC) }
    var selectedTags by remember { mutableStateOf<Set<String>>(emptySet()) }
    var enableComments by remember { mutableStateOf(true) }
    var enableLikes by remember { mutableStateOf(true) }
    var hasMedia by remember { mutableStateOf(false) }
    
    val isValidPost = postContent.trim().isNotEmpty()
    
    val config = CreationScreenConfig(
        title = "Create Post",
        saveButtonText = "Share",
        cancelButtonText = "Discard",
        steps = listOf(
            CreationStep("Content") {
                PostContentStep(
                    content = postContent,
                    onContentChange = { postContent = it }
                )
            },
            CreationStep("Visibility & Audience") {
                PostVisibilityStep(
                    selectedVisibility = selectedVisibility,
                    onVisibilityChange = { selectedVisibility = it }
                )
            },
            CreationStep("Tags & Topics") {
                PostTagsStep(
                    selectedTags = selectedTags,
                    onTagsChange = { selectedTags = it }
                )
            },
            CreationStep("Settings") {
                PostSettingsStep(
                    enableComments = enableComments,
                    onCommentsChange = { enableComments = it },
                    enableLikes = enableLikes,
                    onLikesChange = { enableLikes = it },
                    hasMedia = hasMedia,
                    onMediaChange = { hasMedia = it }
                )
            }
        )
    )
    
    CreationScreen(
        config = config,
        onSave = {
            // TODO: Save post logic
            navigator.pop()
        },
        onCancel = {
            navigator.pop()
        },
        isSaveEnabled = isValidPost
    )
}

/**
 * Post visibility options
 */
enum class PostVisibility(val displayName: String, val description: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    PUBLIC("Public", "Anyone can see this post", Icons.Default.Public),
    FRIENDS("Friends", "Only your friends can see this", Icons.Default.Group),
    FOLLOWERS("Followers", "Your followers can see this", Icons.Default.People),
    PRIVATE("Private", "Only you can see this", Icons.Default.Lock)
}

/**
 * Post content input step
 */
@Composable
private fun PostContentStep(
    content: String,
    onContentChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
    ) {
        // Author preview
        Row(
            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
            verticalAlignment = Alignment.Top
        ) {
            UserAvatar(
                imageUrl = null,
                size = 40.dp
            )
            Column {
                Text(
                    text = "Your Name", // TODO: Get from user context
                    style = UniVibeDesign.Text.cardTitle()
                )
                Text(
                    text = "@username", // TODO: Get from user context
                    style = UniVibeDesign.Text.caption()
                )
            }
        }
        
        // Content input
        OutlinedTextField(
            value = content,
            onValueChange = onContentChange,
            placeholder = { 
                Text(
                    "What's on your mind?",
                    style = UniVibeDesign.Text.body()
                ) 
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Default
            ),
            shape = UniVibeDesign.Cards.standardShape,
            maxLines = 10
        )
        
        // Character count
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${content.length}/280",
                style = UniVibeDesign.Text.caption(),
                color = if (content.length > 280) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
        
        // Media attachment buttons
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
            contentPadding = PaddingValues(vertical = UniVibeDesign.Spacing.sm)
        ) {
            item {
                MediaAttachmentButton(
                    icon = Icons.Default.Image,
                    label = "Photo",
                    onClick = { /* TODO: Add photo */ }
                )
            }
            item {
                MediaAttachmentButton(
                    icon = Icons.Default.VideoLibrary,
                    label = "Video",
                    onClick = { /* TODO: Add video */ }
                )
            }
            item {
                MediaAttachmentButton(
                    icon = Icons.Default.Poll,
                    label = "Poll",
                    onClick = { /* TODO: Add poll */ }
                )
            }
            item {
                MediaAttachmentButton(
                    icon = Icons.Default.LocationOn,
                    label = "Location",
                    onClick = { /* TODO: Add location */ }
                )
            }
        }
    }
}

/**
 * Media attachment button
 */
@Composable
private fun MediaAttachmentButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(UniVibeDesign.Cards.standardShape)
            .clickable { onClick() }
            .padding(UniVibeDesign.Spacing.sm),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = UniVibeDesign.Text.caption()
        )
    }
}

/**
 * Post visibility selection step
 */
@Composable
private fun PostVisibilityStep(
    selectedVisibility: PostVisibility,
    onVisibilityChange: (PostVisibility) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
    ) {
        Text(
            text = "Who can see your post?",
            style = UniVibeDesign.Text.bodySecondary(),
            modifier = Modifier.padding(bottom = UniVibeDesign.Spacing.sm)
        )
        
        PostVisibility.entries.forEach { visibility ->
            UniVibeDesign.ListItemCard(
                onClick = { onVisibilityChange(visibility) },
                leading = {
                    Icon(
                        imageVector = visibility.icon,
                        contentDescription = null,
                        tint = if (selectedVisibility == visibility) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                title = visibility.displayName,
                subtitle = visibility.description,
                trailing = {
                    RadioButton(
                        selected = selectedVisibility == visibility,
                        onClick = { onVisibilityChange(visibility) }
                    )
                }
            )
        }
    }
}

/**
 * Post tags selection step
 */
@Composable
private fun PostTagsStep(
    selectedTags: Set<String>,
    onTagsChange: (Set<String>) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
    ) {
        Text(
            text = "Add topics to help others discover your post",
            style = UniVibeDesign.Text.bodySecondary()
        )
        
        val suggestedTags = listOf(
            "Study Tips", "Campus Life", "Technology", "Sports", 
            "Events", "Food", "Academic", "Career", "Social", 
            "Research", "Clubs", "Projects"
        )
        
        LazyColumn(
            modifier = Modifier.height(200.dp),
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
        ) {
            items(suggestedTags.chunked(2)) { tagPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
                ) {
                    tagPair.forEach { tag ->
                        val isSelected = selectedTags.contains(tag)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) {
                                    onTagsChange(selectedTags - tag)
                                } else {
                                    onTagsChange(selectedTags + tag)
                                }
                            },
                            label = { Text(tag) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (tagPair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        
        if (selectedTags.isNotEmpty()) {
            Text(
                text = "Selected: ${selectedTags.joinToString(", ")}",
                style = UniVibeDesign.Text.caption(),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Post settings step
 */
@Composable
private fun PostSettingsStep(
    enableComments: Boolean,
    onCommentsChange: (Boolean) -> Unit,
    enableLikes: Boolean,
    onLikesChange: (Boolean) -> Unit,
    hasMedia: Boolean,
    onMediaChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
    ) {
        Text(
            text = "Post interaction settings",
            style = UniVibeDesign.Text.bodySecondary(),
            modifier = Modifier.padding(bottom = UniVibeDesign.Spacing.sm)
        )
        
        SettingsToggleCard(
            icon = Icons.Outlined.Comment,
            title = "Enable Comments",
            description = "Allow others to comment on your post",
            checked = enableComments,
            onCheckedChange = onCommentsChange
        )
        
        SettingsToggleCard(
            icon = Icons.Outlined.FavoriteBorder,
            title = "Enable Likes",
            description = "Allow others to like your post",
            checked = enableLikes,
            onCheckedChange = onLikesChange
        )
        
        SettingsToggleCard(
            icon = Icons.Outlined.Notifications,
            title = "Push Notifications",
            description = "Get notified about interactions on this post",
            checked = true,
            onCheckedChange = { /* TODO: Handle notifications */ }
        )
    }
}

/**
 * Settings toggle card component
 */
@Composable
private fun SettingsToggleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    UniVibeDesign.ListItemCard(
        onClick = { onCheckedChange(!checked) },
        leading = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = title,
        subtitle = description,
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    )
}