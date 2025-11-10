package com.example.univibe.ui.screens.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.domain.models.StoryDraft
import com.example.univibe.domain.models.StoryType
import com.example.univibe.ui.theme.BrandColors
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.util.UUID
import kotlin.random.Random

/**
 * CreateStoryScreen - First step in story creation flow
 * Allows user to select image source (camera or gallery)
 *
 * Flow:
 * 1. User sees two options: Take Photo or Choose from Gallery
 * 2. User selects an option
 * 3. Image is loaded and StoryEditorScreen opens with initial draft
 */
class CreateStoryScreen : Screen {
    @Composable
    override fun Content() {
        CreateStoryScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateStoryScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    var showGalleryPicker by remember { mutableStateOf(false) }
    var showCameraOption by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Close Button
        IconButton(
            onClick = { navigator.pop() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
                .size(44.dp)
                .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
        ) {
            Icon(
                imageVector = PlatformIcons.Close,
                contentDescription = "Close",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Create Story",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Choose how you'd like to add a photo",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Option Cards with Animations
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
            ) {
                StoryCreationOption(
                    icon = PlatformIcons.Camera,
                    title = "Take Photo",
                    description = "Use your device camera",
                    onClick = {
                        showCameraOption = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { 100 })
            ) {
                StoryCreationOption(
                    icon = PlatformIcons.Image,
                    title = "Choose from Gallery",
                    description = "Select from your photos",
                    onClick = {
                        showGalleryPicker = true
                    }
                )
            }
        }

        // Camera Option Dialog (Simulated)
        if (showCameraOption) {
            CameraOptionDialog(
                onDismiss = { showCameraOption = false },
                onPhotoSelected = { imageUri ->
                    val draft = StoryDraft(
                        id = UUID.randomUUID().toString(),
                        imageUri = imageUri,
                        type = StoryType.IMAGE
                    )
                    navigator.push(StoryEditorScreen(draft))
                }
            )
        }

        // Gallery Picker Dialog (Simulated)
        if (showGalleryPicker) {
            GalleryPickerDialog(
                onDismiss = { showGalleryPicker = false },
                onPhotoSelected = { imageUri ->
                    val draft = StoryDraft(
                        id = UUID.randomUUID().toString(),
                        imageUri = imageUri,
                        type = StoryType.IMAGE
                    )
                    navigator.push(StoryEditorScreen(draft))
                }
            )
        }
    }
}

/**
 * Reusable card for story creation options
 */
@Composable
private fun StoryCreationOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = BrandColors.Burgundy.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = BrandColors.Burgundy,
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Icon(
            imageVector = PlatformIcons.ChevronRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(24.dp)
        )
    }
}

/**
 * Dialog for camera option (placeholder implementation)
 * In production, would use platform-specific camera APIs
 */
@Composable
private fun CameraOptionDialog(
    onDismiss: () -> Unit,
    onPhotoSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Camera") },
        text = {
            Text(
                "Camera access would be requested here.\n\n" +
                "In production, this would integrate with:\n" +
                "- Android: MediaStore/Intent\n" +
                "- iOS: PHPickerViewController\n\n" +
                "For demo purposes, selecting a sample image."
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    // Simulate camera photo capture
                    val sampleImageUri = "https://images.unsplash.com/photo-1552993894-4243ac4ba358?w=500&h=500&fit=crop"
                    onPhotoSelected(sampleImageUri)
                    onDismiss()
                }
            ) {
                Text("Use Sample Photo")
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
 * Dialog for gallery picker (placeholder implementation)
 * In production, would use platform-specific image picker
 */
@Composable
private fun GalleryPickerDialog(
    onDismiss: () -> Unit,
    onPhotoSelected: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Choose from Gallery") },
        text = {
            Column {
                Text(
                    "Gallery picker would be shown here.\n\n" +
                    "In production, this would integrate with:\n" +
                    "- Android: DocumentsContract\n" +
                    "- iOS: PHPickerViewController\n\n" +
                    "Showing sample gallery images below:"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sample gallery images
                val sampleImages = listOf(
                    "https://images.unsplash.com/photo-1516309022940-8428b4c20948?w=500&h=500&fit=crop",
                    "https://images.unsplash.com/photo-1498855926480-d98e83099315?w=500&h=500&fit=crop",
                    "https://images.unsplash.com/photo-1505576399279-565b52d4ac71?w=500&h=500&fit=crop"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    sampleImages.forEachIndexed { index, imageUrl ->
                        androidx.compose.material3.Surface(
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    onPhotoSelected(imageUrl)
                                    onDismiss()
                                },
                            color = BrandColors.Burgundy.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        color = BrandColors.Burgundy.copy(alpha = 0.3f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
