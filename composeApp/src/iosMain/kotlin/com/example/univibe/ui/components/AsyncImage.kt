package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

/**
 * iOS implementation of AsyncImage placeholder
 * Coil 3 is not available for iOS, so this is a placeholder
 * In production, would need to use platform-specific image loading via expect/actual
 */
@Composable
actual fun AsyncImage(
    model: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    Box(
        modifier = modifier.background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}