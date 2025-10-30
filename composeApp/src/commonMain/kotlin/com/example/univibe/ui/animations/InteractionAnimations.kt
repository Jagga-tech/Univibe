package com.example.univibe.ui.animations

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Interactive animation utilities for button clicks, likes, and user feedback.
 * Creates delightful micro-interactions throughout the app.
 */
object InteractionAnimations {
    
    /**
     * Scale animation modifier for clickable elements.
     * Scales down when pressed, returns to normal on release.
     */
    fun Modifier.scaleOnPress(): Modifier = composed {
        var isPressed by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (isPressed) 0.95f else 1f,
            animationSpec = AnimationConstants.quickSpec,
            label = "scaleOnPress"
        )
        
        val interactionSource = remember { MutableInteractionSource() }
        
        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> isPressed = true
                    is PressInteraction.Release -> isPressed = false
                    is PressInteraction.Cancel -> isPressed = false
                }
            }
        }
        
        this
            .scale(scale)
            .clickable(interactionSource = interactionSource, indication = null) {}
    }
    
    /**
     * Bounce animation - scales up then back down smoothly.
     * Great for confirmations and success states.
     */
    @Composable
    fun bounceAnimation(
        trigger: Boolean = false,
        duration: Int = AnimationConstants.veryFastDuration
    ): Float {
        var animationTrigger by remember { mutableStateOf(false) }
        
        LaunchedEffect(trigger) {
            if (trigger) {
                animationTrigger = true
            }
        }
        
        val scale by animateFloatAsState(
            targetValue = if (animationTrigger) 1f else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessHigh
            ),
            label = "bounce",
            finishedListener = {
                if (trigger) {
                    animationTrigger = false
                }
            }
        )
        
        return scale
    }
    
    /**
     * Like button animation - heart scales up briefly when liked.
     */
    @Composable
    fun likeButtonAnimation(isLiked: Boolean): Float {
        val scale by animateFloatAsState(
            targetValue = if (isLiked) 1.3f else 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessHigh
            ),
            label = "likeButtonScale"
        )
        
        return scale
    }
    
    /**
     * Follow button animation - color transition + scale.
     */
    @Composable
    fun followButtonAnimation(isFollowed: Boolean): Float {
        val scale by animateFloatAsState(
            targetValue = if (isFollowed) 1.05f else 1f,
            animationSpec = AnimationConstants.quickSpec,
            label = "followButtonScale"
        )
        
        return scale
    }
    
    /**
     * Send button animation - slight rotation + scale.
     */
    @Composable
    fun sendButtonAnimation(isPressed: Boolean): Pair<Float, Float> {
        val scale by animateFloatAsState(
            targetValue = if (isPressed) 1.1f else 1f,
            animationSpec = AnimationConstants.quickSpec,
            label = "sendScale"
        )
        
        val rotation by animateFloatAsState(
            targetValue = if (isPressed) 10f else 0f,
            animationSpec = AnimationConstants.quickSpec,
            label = "sendRotation"
        )
        
        return Pair(scale, rotation)
    }
    
    /**
     * FAB animation - rotation while active/loading.
     */
    @Composable
    fun fabRotationAnimation(isActive: Boolean): Float {
        val rotation by animateFloatAsState(
            targetValue = if (isActive) 360f else 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 2000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "fabRotation"
        )
        
        return rotation
    }
    
    /**
     * Pulse animation - element pulses in and out.
     */
    @Composable
    fun pulseAnimation(): Float {
        val scale by animateFloatAsState(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = AnimationConstants.infiniteDuration,
                    easing = EaseInOutCubic
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse"
        )
        
        return scale
    }
    
    /**
     * Shake animation - for error states.
     */
    @Composable
    fun shakeAnimation(shouldShake: Boolean): Float {
        val offsetX by animateFloatAsState(
            targetValue = if (shouldShake) 0f else 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 100,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "shake"
        )
        
        return offsetX
    }
}

/**
 * Extension modifier for animating content size changes.
 */
fun Modifier.animateSizeChange(): Modifier = animateContentSize(
    animationSpec = tween(
        durationMillis = AnimationConstants.standardDuration,
        easing = AnimationConstants.standardEasing
    )
)