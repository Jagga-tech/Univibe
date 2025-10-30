package com.example.univibe.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

/**
 * Image utilities for placeholders, gradients, and user initials.
 */
object ImageUtils {
    
    /**
     * Generate a color from a user ID or string for consistent avatar colors.
     */
    fun generateColorFromId(id: String): Color {
        val colors = listOf(
            Color(0xFF6200EE), // Purple
            Color(0xFF03DAC6), // Teal
            Color(0xFFCF6679), // Pink
            Color(0xFFBB86FC), // Light Purple
            Color(0xFF018786), // Dark Teal
            Color(0xFFFF6B9D), // Rose
            Color(0xFFC854C0), // Magenta
            Color(0xFF5C6BC0), // Indigo
            Color(0xFF29B6F6), // Light Blue
            Color(0xFF66BB6A)  // Green
        )
        
        val hash = id.hashCode()
        val index = abs(hash) % colors.size
        return colors[index]
    }
    
    /**
     * Extract initials from a full name.
     */
    fun getInitials(name: String): String {
        return name.split(" ")
            .mapNotNull { it.firstOrNull() }
            .take(2)
            .joinToString("")
            .uppercase()
    }
    
    /**
     * Generate a gradient brush for placeholder images.
     */
    fun generateGradientBrush(id: String): Brush {
        val color1 = generateColorFromId(id)
        val color2 = generateColorFromId(id + "_1")
        
        return Brush.linearGradient(
            colors = listOf(color1, color2)
        )
    }
}

/**
 * Profile image placeholder with gradient background and initials.
 */
@Composable
fun ProfileImagePlaceholder(
    name: String,
    userId: String,
    size: Dp = 48.dp
) {
    val initials = ImageUtils.getInitials(name)
    val backgroundColor = ImageUtils.generateColorFromId(userId)
    
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = (size / 2).value.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

/**
 * Post image placeholder with shimmer and error state support.
 */
@Composable
fun PostImagePlaceholder(
    modifier: Modifier = Modifier,
    aspectRatio: Float = 16f / 9f,
    contentType: String = "image"
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Image,
            contentDescription = "Image placeholder",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Event cover placeholder with gradient and icon.
 */
@Composable
fun EventCoverPlaceholder(
    eventId: String,
    eventName: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = ImageUtils.generateColorFromId(eventId)
    val gradientBrush = ImageUtils.generateGradientBrush(eventId)
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush = gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = eventName.take(1),
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

/**
 * Club/Group logo placeholder with initials.
 */
@Composable
fun ClubLogoPlaceholder(
    clubName: String,
    clubId: String,
    size: Dp = 64.dp
) {
    val initials = ImageUtils.getInitials(clubName)
    val backgroundColor = ImageUtils.generateColorFromId(clubId)
    
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = (size / 2.5f).value.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

/**
 * Empty state illustration placeholder.
 */
@Composable
fun EmptyStatePlaceholder(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}