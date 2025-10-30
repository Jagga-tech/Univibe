package com.example.univibe.ui.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * List and scroll animations - staggered entry, pull-to-refresh, scroll-triggered effects.
 */
object ListAnimations {
    
    /**
     * Staggered fade-in animation for list items.
     * Each item appears with a slight delay creating a cascade effect.
     */
    @Composable
    fun AnimatedListItem(
        index: Int,
        staggerDelayMillis: Long = 50L,
        content: @Composable () -> Unit
    ) {
        var visible by remember { mutableStateOf(false) }
        
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(index * staggerDelayMillis)
            visible = true
        }
        
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = AnimationConstants.standardDuration,
                    easing = AnimationConstants.standardEasing
                )
            ) + slideInVertically(
                initialOffsetY = { it / 4 },
                animationSpec = tween(
                    durationMillis = AnimationConstants.standardDuration,
                    easing = AnimationConstants.standardEasing
                )
            ),
            exit = fadeOut() + slideOutVertically()
        ) {
            content()
        }
    }
    
    /**
     * Parallel animation - fade in + scale in for list items.
     */
    @Composable
    fun AnimatedListItemScale(
        index: Int,
        staggerDelayMillis: Long = 30L,
        content: @Composable () -> Unit
    ) {
        var visible by remember { mutableStateOf(false) }
        
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(index * staggerDelayMillis)
            visible = true
        }
        
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = AnimationConstants.standardDuration
                )
            ) + scaleIn(
                initialScale = 0.8f,
                animationSpec = tween(
                    durationMillis = AnimationConstants.standardDuration,
                    easing = AnimationConstants.standardEasing
                )
            ),
            exit = fadeOut() + scaleOut()
        ) {
            content()
        }
    }
    
    /**
     * Refresh indicator animation - rotating indicator.
     */
    @Composable
    fun refreshIndicatorAnimation(): Float {
        val transition = rememberInfiniteTransition(label = "refresh")
        
        val rotation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "refreshRotation"
        )
        
        return rotation
    }
    
    /**
     * Scroll-triggered header collapse animation.
     */
    @Composable
    fun headerCollapseAnimation(scrollProgress: Float): Float {
        return animateFloatAsState(
            targetValue = scrollProgress,
            animationSpec = tween(
                durationMillis = 200,
                easing = AnimationConstants.standardEasing
            ),
            label = "headerCollapse"
        ).value
    }
    
    /**
     * Parallax scroll effect for header background.
     */
    @Composable
    fun parallaxAnimation(scrollOffset: Float, parallaxFactor: Float = 0.5f): Float {
        return scrollOffset * parallaxFactor
    }
    
    /**
     * FAB hide/show on scroll animation.
     */
    @Composable
    fun fabScrollAnimation(shouldShow: Boolean): Float {
        return animateFloatAsState(
            targetValue = if (shouldShow) 0f else 1f,
            animationSpec = tween(
                durationMillis = AnimationConstants.standardDuration,
                easing = AnimationConstants.standardEasing
            ),
            label = "fabScroll"
        ).value
    }
}

// Helper for importing kotlinx.coroutines
private object _CoroutineHelper {
    suspend fun delay(ms: Long) = kotlinx.coroutines.delay(ms)
}