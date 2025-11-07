package com.example.univibe.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

/**
 * Loading state animations - shimmer effects, progress indicators, and skeleton screens.
 */
object LoadingAnimations {
    
    /**
     * Shimmer effect for content loading.
     * Creates a sweeping light gradient across the component.
     */
    @Composable
    fun ShimmerEffect(modifier: Modifier = Modifier) {
        val transition = rememberInfiniteTransition(label = "shimmer")
        
        val translateAnim = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = AnimationConstants.shimmerDuration,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmerTranslate"
        ).value
        
        val brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ),
            start = Offset(translateAnim - 1000f, 0f),
            end = Offset(translateAnim, 0f)
        )
        
        Box(
            modifier = modifier.background(brush)
        )
    }
    
    /**
     * Circular progress animation for indeterminate progress.
     */
    @Composable
    fun circularProgressAnimation(): Float {
        val transition = rememberInfiniteTransition(label = "circularProgress")
        
        val rotation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "progressRotation"
        ).value
        
        return rotation
    }
    
    /**
     * Linear progress animation for determinate progress.
     */
    @Composable
    fun linearProgressAnimation(progress: Float): Float {
        return animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(
                durationMillis = AnimationConstants.standardDuration,
                easing = AnimationConstants.standardEasing
            ),
            label = "linearProgress"
        ).value
    }
    
    /**
     * Uploading animation with percentage.
     */
    @Composable
    fun uploadProgressAnimation(currentProgress: Float): Pair<Float, String> {
        val animatedProgress = animateFloatAsState(
            targetValue = currentProgress,
            animationSpec = tween<Float>(
                durationMillis = 500,
                easing = EaseInOutCubic
            ),
            label = "uploadProgress"
        ).value
        
        val percentage = (animatedProgress * 100).toInt()
        
        return Pair(animatedProgress, "$percentage%")
    }
    
    /**
     * Success checkmark animation - draws checkmark with scale and rotation.
     */
    @Composable
    fun successCheckmarkAnimation(): Float {
        val transition = rememberInfiniteTransition(label = "successCheckmark")
        
        val scale = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween<Float>(
                durationMillis = 500,
                easing = AnimationConstants.standardEasing
            ),
            label = "checkmarkScale"
        ).value
        
        return scale
    }
    
    /**
     * Bounce animation for loading indicators.
     */
    @Composable
    fun bounceLoadingAnimation(): Float {
        val transition = rememberInfiniteTransition(label = "bounceLoading")
        
        val offsetY = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 600,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bounceOffset"
        ).value
        
        return offsetY
    }
    
    /**
     * Pulsing animation for importance/attention.
     */
    @Composable
    fun pulsingAnimation(startAlpha: Float = 0.3f, endAlpha: Float = 1f): Float {
        val transition = rememberInfiniteTransition(label = "pulsing")
        
        val alpha = transition.animateFloat(
            initialValue = startAlpha,
            targetValue = endAlpha,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = EaseInOutCubic
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulsingAlpha"
        ).value
        
        return alpha
    }
}