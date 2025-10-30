package com.example.univibe.ui.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Feedback and success animations - confirmations, error states, toast notifications.
 */
object FeedbackAnimations {
    
    /**
     * Success checkmark animation composable.
     */
    @Composable
    fun AnimatedCheckmark(
        modifier: Modifier = Modifier,
        tint: Color = Color.Green
    ) {
        var visible by remember { mutableStateOf(false) }
        
        LaunchedEffect(Unit) {
            visible = true
        }
        
        val scale by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessHigh
            ),
            label = "checkmarkScale"
        )
        
        if (visible) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Success",
                tint = tint,
                modifier = modifier
                    .scale(scale)
                    .animateContentSize()
            )
        }
    }
    
    /**
     * Error icon animation - shakes and scales.
     */
    @Composable
    fun AnimatedErrorIcon(
        modifier: Modifier = Modifier,
        tint: Color = Color.Red
    ) {
        var visible by remember { mutableStateOf(false) }
        
        LaunchedEffect(Unit) {
            visible = true
        }
        
        val scale by animateFloatAsState(
            targetValue = if (visible) 1f else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessHigh
            ),
            label = "errorScale"
        )
        
        if (visible) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                tint = tint,
                modifier = modifier.scale(scale)
            )
        }
    }
    
    /**
     * Toast notification animation - slides up + fade in.
     */
    @Composable
    fun toastNotificationAnimation(isVisible: Boolean): Pair<Float, Float> {
        val alphaAnimation by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0f,
            animationSpec = tween(
                durationMillis = AnimationConstants.standardDuration,
                easing = AnimationConstants.standardEasing
            ),
            label = "toastAlpha"
        )
        
        val translationAnimation by animateFloatAsState(
            targetValue = if (isVisible) 0f else 100f,
            animationSpec = tween(
                durationMillis = AnimationConstants.standardDuration,
                easing = AnimationConstants.standardEasing
            ),
            label = "toastTranslation"
        )
        
        return Pair(alphaAnimation, translationAnimation)
    }
    
    /**
     * Snackbar animation - slides up from bottom.
     */
    @Composable
    fun snackbarAnimation(isVisible: Boolean): Float {
        return animateFloatAsState(
            targetValue = if (isVisible) 0f else 1f,
            animationSpec = tween(
                durationMillis = AnimationConstants.standardDuration,
                easing = AnimationConstants.standardEasing
            ),
            label = "snackbarOffset"
        ).value
    }
    
    /**
     * Confetti animation trigger - for celebrations.
     */
    @Composable
    fun confettiAnimation(trigger: Boolean): Boolean {
        var animationActive by remember { mutableStateOf(false) }
        
        LaunchedEffect(trigger) {
            if (trigger) {
                animationActive = true
                kotlinx.coroutines.delay(500)
                animationActive = false
            }
        }
        
        return animationActive
    }
    
    /**
     * Ripple animation for button clicks.
     */
    @Composable
    fun rippleAnimation(isActive: Boolean): Float {
        val scale by animateFloatAsState(
            targetValue = if (isActive) 2f else 0f,
            animationSpec = tween(
                durationMillis = 600,
                easing = EaseOut
            ),
            label = "rippleScale"
        )
        
        return scale
    }
    
    /**
     * Status color animation - smooth color transitions for state changes.
     */
    @Composable
    fun statusColorAnimation(status: IndicatorStatus): Color {
        val targetColor = when (status) {
            IndicatorStatus.SUCCESS -> Color.Green
            IndicatorStatus.ERROR -> Color.Red
            IndicatorStatus.WARNING -> Color.Yellow
            IndicatorStatus.INFO -> MaterialTheme.colorScheme.primary
            IndicatorStatus.LOADING -> MaterialTheme.colorScheme.primary
        }
        
        return animateColorAsState(
            targetValue = targetColor,
            animationSpec = tween(
                durationMillis = AnimationConstants.standardDuration,
                easing = AnimationConstants.standardEasing
            ),
            label = "statusColor"
        ).value
    }
    
    /**
     * Notification badge animation - pulse and scale.
     */
    @Composable
    fun notificationBadgeAnimation(hasNotification: Boolean): Float {
        val scale by animateFloatAsState(
            targetValue = if (hasNotification) 1.2f else 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessHigh
            ),
            label = "badgeScale"
        )
        
        return scale
    }
}

/**
 * Indicator status enum for feedback animations.
 */
enum class IndicatorStatus {
    SUCCESS, ERROR, WARNING, INFO, LOADING
}