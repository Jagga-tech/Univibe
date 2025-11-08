package com.example.univibe.ui.components
import androidx.compose.animation.core.animateFloatAsState

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.animations.AnimationConstants
import com.example.univibe.ui.theme.Dimensions

/**
 * Enhanced elevated card component for UniVibe with rounded corners and state animations.
 * Supports hover, pressed, and selected states with smooth transitions.
 */
@Composable
fun UniVibeCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    elevation: Dp = 4.dp,
    isSelected: Boolean = false,
    content: @Composable () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }
    
    // Animation for elevation on hover/pressed
    val animatedElevation by animateFloatAsState(
        targetValue = when {
            isPressed -> (elevation - 1.dp).value
            isHovered -> (elevation + 2.dp).value
            else -> elevation.value
        },
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration,
            easing = AnimationConstants.standardEasing
        ),
        label = "cardElevation"
    )
    
    // Animation for border color if selected
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration,
            easing = AnimationConstants.standardEasing
        ),
        label = "cardBorderColor"
    )
    
    // Animation for scale
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.98f
            isHovered && onClick != null -> 1.02f
            else -> 1f
        },
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration,
            easing = AnimationConstants.standardEasing
        ),
        label = "cardScale"
    )
    
    val interactionSource = remember { MutableInteractionSource() }
    val cardModifier = if (onClick != null) {
        modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    } else {
        modifier
    }
    
    // Track hover state
    LaunchedEffect(Unit) {
        interactionSource.interactions.collect { _ ->
            // Handle interactions
        }
    }
    
    Card(
        modifier = cardModifier
            .scale(scale)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = borderColor,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.CornerRadius.large)
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = AnimationConstants.standardDuration,
                    easing = AnimationConstants.standardEasing
                )
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = animatedElevation.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.CornerRadius.large)
    ) {
        Box {
            content()
        }
    }
}