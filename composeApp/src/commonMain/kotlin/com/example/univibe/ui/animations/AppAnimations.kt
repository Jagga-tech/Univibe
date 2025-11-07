package com.example.univibe.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.Modifier

/**
 * Standard animation specifications used throughout the app.
 * Provides consistent timing and easing for all animations.
 * 
 * Note: Use these specs with AnimatedVisibility, AnimatedContent, or similar Composables.
 * Example: AnimatedVisibility(visible = isVisible, enter = fadeInSpec) { ... }
 */
object AppAnimations {
    
    // Duration constants (milliseconds)
    const val FAST_DURATION = 150
    const val STANDARD_DURATION = 300
    const val SLOW_DURATION = 500
    const val VERY_SLOW_DURATION = 700
    
    // Standard tween transitions
    val fastTransition = tween<Float>(
        durationMillis = FAST_DURATION,
        easing = FastOutSlowInEasing
    )
    
    val standardTransition = tween<Float>(
        durationMillis = STANDARD_DURATION,
        easing = FastOutSlowInEasing
    )
    
    val slowTransition = tween<Float>(
        durationMillis = SLOW_DURATION,
        easing = FastOutSlowInEasing
    )
    
    val verySlowTransition = tween<Float>(
        durationMillis = VERY_SLOW_DURATION,
        easing = FastOutSlowInEasing
    )
    
    // Spring animations for bouncy effects
    val standardSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
    
    val bouncySpring = spring<Float>(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    // AnimatedVisibility specifications - using correct type specifications
    val fadeInSpec = fadeIn(tween(durationMillis = STANDARD_DURATION))
    val fadeOutSpec = fadeOut(tween(durationMillis = STANDARD_DURATION))
    
    val slideInFromBottomSpec = slideInVertically(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        initialOffsetY = { it }
    )
    
    val slideOutToBottomSpec = slideOutVertically(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        targetOffsetY = { it }
    )
    
    val slideInFromRightSpec = slideInHorizontally(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        initialOffsetX = { it }
    )
    
    val slideOutToRightSpec = slideOutHorizontally(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        targetOffsetX = { it }
    )
    
    val slideInFromLeftSpec = slideInHorizontally(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        initialOffsetX = { -it }
    )
    
    val slideOutToLeftSpec = slideOutHorizontally(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        targetOffsetX = { -it }
    )
    
    val slideInFromTopSpec = slideInVertically(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        initialOffsetY = { -it }
    )
    
    val slideOutToTopSpec = slideOutVertically(
        animationSpec = tween(durationMillis = STANDARD_DURATION),
        targetOffsetY = { -it }
    )
    
    val scaleInSpec = scaleIn(
        animationSpec = tween(durationMillis = FAST_DURATION),
        initialScale = 0.8f
    )
    
    val scaleOutSpec = scaleOut(
        animationSpec = tween(durationMillis = FAST_DURATION),
        targetScale = 0.8f
    )
    
    // Combined animations
    val fadeScaleInSpec = fadeInSpec + scaleInSpec
    val fadeScaleOutSpec = fadeOutSpec + scaleOutSpec
}

/**
 * Shared element style animations for list items
 */
object SharedElementTransitions {
    val boundsTransform = tween<androidx.compose.ui.geometry.Rect>(
        durationMillis = 500,
        easing = FastOutSlowInEasing
    )
    
    val boundsTransformFast = tween<androidx.compose.ui.geometry.Rect>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
}
