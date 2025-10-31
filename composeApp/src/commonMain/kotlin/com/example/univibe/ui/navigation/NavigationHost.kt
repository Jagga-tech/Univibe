package com.example.univibe.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen as VoyagerScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

/**
 * Main Navigation Host
 * 
 * Root navigation container that manages screen transitions using Voyager
 */
@Composable
fun NavigationHost(
    initialScreen: VoyagerScreen,
    onBackPressed: (VoyagerScreen) -> Boolean = { false }
) {
    Navigator(
        screen = initialScreen,
        onBackPressed = onBackPressed
    ) { navigator ->
        SlideTransition(
            navigator = navigator,
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        )
    }
}

/**
 * Slide transition animations for navigation
 */
object NavigationTransitions {
    fun slideInFromRight() = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + fadeIn(animationSpec = tween(300))
    
    fun slideOutToLeft() = slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(300))
    
    fun slideInFromLeft() = slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + fadeIn(animationSpec = tween(300))
    
    fun slideOutToRight() = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(300))
    
    fun fadeIn() = fadeIn(animationSpec = tween(300))
    fun fadeOut() = fadeOut(animationSpec = tween(300))
}