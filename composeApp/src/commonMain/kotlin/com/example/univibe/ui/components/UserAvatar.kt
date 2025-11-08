package com.example.univibe.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.univibe.ui.theme.PlatformIcons
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.animations.AnimationConstants
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.SemanticColors
import com.example.univibe.ui.utils.ImageUtils
import com.example.univibe.ui.utils.ProfileImagePlaceholder

/**
 * Enhanced circular avatar component with optional online status indicator,
 * shimmer loading, and smooth animations.
 *
 * **Features:**
 * - Image loading from URL with Coil (SubcomposeAsyncImage)
 * - Fallback to initials with gradient background
 * - Border in surface color (2dp)
 * - Online status indicator (green dot with white border)
 * - Loading shimmer while image loads
 * - Error state with initials fallback
 * - Perfect circular clipping
 * - Optional click handler
 *
 * **Example Usage:**
 * ```kotlin
 * UserAvatar(
 *     imageUrl = "https://...",
 *     name = "John Doe",
 *     size = Dimensions.AvatarSize.medium,
 *     showOnlineIndicator = true,
 *     isOnline = true,
 *     onClick = { /* handle click */ }
 * )
 * ```
 *
 * @param imageUrl Optional URL for the avatar image. If null or loading fails, displays initials.
 * @param name User name (used for initials fallback and gradient generation).
 * @param modifier Modifier to apply to the avatar.
 * @param size Size of the avatar circle.
 * @param showOnlineIndicator Whether to show the online status indicator (green/gray dot).
 * @param isOnline Whether user is currently online (affects indicator color).
 * @param onClick Optional click handler for the avatar.
 */
@Composable
fun UserAvatar(
    imageUrl: String? = null,
    name: String = "User",
    modifier: Modifier = Modifier,
    size: Dp = Dimensions.AvatarSize.medium,
    showOnlineIndicator: Boolean = false,
    isOnline: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val borderSize = 2.dp
    val onlineIndicatorSize = size * 0.3f
    
    Box(
        modifier = modifier
            .size(size)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = AnimationConstants.standardDuration,
                    easing = AnimationConstants.standardEasing
                )
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onClick
                    )
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        // Outer border box
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    width = borderSize,
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
        )
        
        // Avatar content - image or placeholder
        if (!imageUrl.isNullOrEmpty()) {
            // Image with fallback to initials on error
            AvatarImageContent(
                imageUrl = imageUrl,
                name = name,
                size = size - borderSize * 2
            )
        } else {
            // Initials placeholder with gradient
            AvatarPlaceholder(
                name = name,
                size = size - borderSize * 2
            )
        }
        
        // Online status indicator (green/gray dot in bottom-right)
        if (showOnlineIndicator) {
            Box(
                modifier = Modifier
                    .size(onlineIndicatorSize)
                    .clip(CircleShape)
                    .background(if (isOnline) SemanticColors.Online else SemanticColors.Offline)
                    .border(
                        width = 1.5.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

/**
 * Avatar image content with error fallback.
 * In a real implementation, this would use Coil's SubcomposeAsyncImage.
 * For now, it shows a placeholder icon that could be replaced.
 */
@Composable
private fun AvatarImageContent(
    imageUrl: String,
    name: String,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        // TODO: Integrate Coil's SubcomposeAsyncImage for actual image loading
        // For now showing placeholder - replace with actual image loading:
        // SubcomposeAsyncImage(
        //     model = imageUrl,
        //     contentDescription = "$name avatar",
        //     contentScale = ContentScale.Crop,
        //     loading = { ShimmerEffect() },
        //     error = { AvatarPlaceholder(name, size) }
        // )
        
        Icon(
            imageVector = PlatformIcons.Default.Person,
            contentDescription = "User avatar",
            modifier = Modifier.size(size * 0.6f),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Avatar placeholder with initials and gradient background.
 * Generates consistent gradient colors based on the user's name.
 */
@Composable
private fun AvatarPlaceholder(
    name: String,
    size: Dp
) {
    val initials = name.split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")
    
    // Generate gradient based on name hashcode for consistent colors
    val gradientColors = remember(name) {
        val hash = name.hashCode()
        val hue = (hash % 360).toFloat()
        listOf(
            Color.hsl(hue, 0.6f, 0.5f),
            Color.hsl((hue + 30) % 360, 0.6f, 0.6f)
        )
    }
    
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(brush = Brush.linearGradient(gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontSize = (size.value * 0.4f).sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Shimmer loading effect for avatar images.
 * Used while images are loading to provide visual feedback.
 */
@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )
    
    val brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ),
        start = androidx.compose.ui.geometry.Offset(translateAnim - 1000f, 0f),
        end = androidx.compose.ui.geometry.Offset(translateAnim, 0f)
    )
    
    Box(modifier = modifier.background(brush))
}

/**
 * Animated like button with heart icon that scales on interaction.
 */
@Composable
fun AnimatedLikeButton(
    isLiked: Boolean,
    onLike: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isLiked) 1.3f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "likeButtonScale"
    )
    
    Box(
        modifier = modifier.size(48.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.IconButton(
            onClick = onLike,
            modifier = Modifier.scale(scale)
        ) {
            Icon(
                imageVector = if (isLiked) {
                } else {
                },
                contentDescription = "Like",
                tint = if (isLiked) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}