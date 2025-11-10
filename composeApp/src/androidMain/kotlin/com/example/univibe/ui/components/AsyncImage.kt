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
 * Android implementation of AsyncImage using Coil
 */
@Composable
actual fun AsyncImage(
    model: String,
    contentDescription: String?,
    modifier: Modifier,
    contentScale: ContentScale
) {
    // For now, using a placeholder
    // In production, integrate Coil 3.0 like this:
    // coil.compose.AsyncImage(
    //     model = model,
    //     contentDescription = contentDescription,
    //     modifier = modifier,
    //     contentScale = contentScale
    // )
    Box(
        modifier = modifier.background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}