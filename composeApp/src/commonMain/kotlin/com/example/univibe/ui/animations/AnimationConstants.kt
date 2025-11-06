package com.example.univibe.ui.animations

import androidx.compose.animation.core.*

/**
 * Centralized animation constants for consistent timing and easing across the app.
 * All animations use these specs for a cohesive user experience.
 */
object AnimationConstants {
    // ============== DURATION SPECS ==============
    
    // Quick feedback animations (very fast)
    val veryFastDuration = 150
    
    // Standard UI animations
    val standardDuration = 300
    
    // Navigation/screen transitions
    val navigationDuration = 300
    
    // Loading/shimmer animations
    val shimmerDuration = 1000
    
    // Slow, elegant animations
    val slowDuration = 500
    
    // Long animations (like infinite loops)
    val infiniteDuration = 2000
    
    // ============== EASING SPECS ==============
    
    // Material Design 3 standard easing
    val standardEasing = FastOutSlowInEasing
    
    // Quick feedback easing (snappy)
    val emphasisEasing = FastOutLinearInEasing
    
    // Smooth, natural movement
    val naturalEasing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
    
    // ============== SPRING SPECS ==============
    
    // Interactive spring (bouncy)
    val interactiveSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessHigh
    )
    
    // Smooth spring (less bouncy)
    val smoothSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    // Stiff spring (minimal bounce)
    val stiffSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessHigh
    )
    
    // ============== COMMON ANIMATION SPECS ==============
    
    // Standard tween animation
    val standardSpec = tween<Float>(
        durationMillis = standardDuration,
        easing = standardEasing
    )
    
    // Quick tween animation
    val quickSpec = tween<Float>(
        durationMillis = veryFastDuration,
        easing = emphasisEasing
    )
    
    // Navigation tween animation
    val navigationSpec = tween<Float>(
        durationMillis = navigationDuration,
        easing = standardEasing
    )
}