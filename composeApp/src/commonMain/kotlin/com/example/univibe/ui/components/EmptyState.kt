package com.example.univibe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions

/**
 * Empty state component displaying an icon, title, description, and optional action button.
 *
 * @param icon ImageVector to display at the top.
 * @param title Title text for the empty state.
 * @param description Description text explaining the empty state.
 * @param actionText Optional text for the action button.
 * @param onAction Optional callback when the action button is clicked.
 */
@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    description: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon
        Icon(
            imageVector = icon,
            contentDescription = "Empty state icon",
            modifier = Modifier
                .size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        // Title
        Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        // Description
        Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        // Action Button
        if (!actionText.isNullOrEmpty() && onAction != null) {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            PrimaryButton(
                text = actionText,
                onClick = onAction
            )
        }
    }
}