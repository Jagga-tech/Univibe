package com.example.univibe.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions

/**
 * Error view component with icon, message, and optional retry button
 * 
 * Usage:
 * if (hasError) {
 *     ErrorView(
 *         message = "Failed to load events",
 *         onRetry = { viewModel.retryLoading() }
 *     )
 * }
 */
@Composable
fun ErrorView(
    message: String = "Something went wrong",
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
            modifier = Modifier.padding(Dimensions.Spacing.xl)
        ) {
            Text(
                text = "✕",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.error
            )
            
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            if (onRetry != null) {
                SecondaryButton(
                    text = "Try Again",
                    onClick = onRetry
                )
            }
        }
    }
}

/**
 * Network error view - specialized for network issues
 */
@Composable
fun NetworkErrorView(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
            modifier = Modifier.padding(Dimensions.Spacing.xl)
        ) {
            Text(
                text = "⛛",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.error
            )
            
            Text(
                text = "No Internet Connection",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Please check your connection and try again",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            SecondaryButton(
                text = "Retry",
                onClick = onRetry
            )
        }
    }
}

/**
 * Inline error message for smaller spaces
 */
@Composable
fun InlineError(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.default),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "✕",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.error
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        
        if (onRetry != null) {
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}