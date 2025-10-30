package com.example.univibe.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

/**
 * Navigation animation transitions for smooth screen navigation.
 * Provides consistent, Material Design 3-compliant transitions.
 */
object NavigationAnimations {
    
    /**
     * Slide in from the right with fade effect (forward navigation enter).
     */
    fun slideInFromRight(): EnterTransition = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration,
            easing = AnimationConstants.standardEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration
        )
    )
    
    /**
     * Slide out to the left with fade effect (forward navigation exit).
     */
    fun slideOutToLeft(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration,
            easing = AnimationConstants.standardEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration
        )
    )
    
    /**
     * Slide in from the left with fade effect (back navigation enter).
     */
    fun slideInFromLeft(): EnterTransition = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration,
            easing = AnimationConstants.standardEasing
        )
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration
        )
    )
    
    /**
     * Slide out to the right with fade effect (back navigation exit).
     */
    fun slideOutToRight(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration,
            easing = AnimationConstants.standardEasing
        )
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConstants.navigationDuration
        )
    )
    
    /**
     * Fade in animation (for tab/bottom nav changes).
     */
    fun fadeInTransition(): EnterTransition = fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration
        )
    )
    
    /**
     * Fade out animation (for tab/bottom nav changes).
     */
    fun fadeOutTransition(): ExitTransition = fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration
        )
    )
    
    /**
     * Scale up animation for dialogs/modals entering.
     */
    fun scaleIn(): EnterTransition = scaleIn(
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration,
            easing = AnimationConstants.standardEasing
        ),
        initialScale = 0.8f
    ) + fadeIn(
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration
        )
    )
    
    /**
     * Scale down animation for dialogs/modals exiting.
     */
    fun scaleOut(): ExitTransition = scaleOut(
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration,
            easing = AnimationConstants.standardEasing
        ),
        targetScale = 0.8f
    ) + fadeOut(
        animationSpec = tween(
            durationMillis = AnimationConstants.standardDuration
        )
    )
}