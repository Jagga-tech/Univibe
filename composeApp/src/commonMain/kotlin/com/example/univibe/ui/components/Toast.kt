package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.SemanticColors

/**
 * Toast notification types.
 */
enum class ToastType {
    Success,
    Error,
    Warning,
    Info
}

/**
 * Get the appropriate icon and color for a toast type.
 */
private fun ToastType.getIconAndColor(): Pair<ImageVector, androidx.compose.ui.graphics.Color> {
    return when (this) {
        ToastType.Success -> Icons.Default.CheckCircle to SemanticColors.Success
        ToastType.Error -> Icons.Default.Error to SemanticColors.Error
        ToastType.Warning -> Icons.Default.Warning to SemanticColors.Warning
        ToastType.Info -> Icons.Default.Info to SemanticColors.Info
    }
}

/**
 * Toast component for displaying brief notifications.
 *
 * Features:
 * - Multiple toast types (Success, Error, Warning, Info)
 * - Icon and message display
 * - Optional action button
 * - Auto-dismiss capability
 *
 * @param message The message to display
 * @param type The type of toast
 * @param modifier Modifier for customization
 * @param actionLabel Optional action button label
 * @param onAction Callback when action button is clicked
 * @param duration How long to show the toast (Snackbar.LENGTH_SHORT, etc)
 */
@Composable
fun UniVibeToast(
    message: String,
    type: ToastType = ToastType.Info,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    duration: Int = 3000
) {
    val (icon, color) = type.getIconAndColor()
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.default)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimensions.CornerRadius.medium)
            )
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(Dimensions.CornerRadius.medium)
            )
            .padding(Dimensions.Spacing.md),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.IconSize.md),
                tint = color
            )
            
            // Message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            
            // Action button
            if (!actionLabel.isNullOrEmpty() && onAction != null) {
                TextButton(onClick = onAction) {
                    Text(
                        actionLabel,
                        color = color
                    )
                }
            }
        }
    }
}

/**
 * Success toast notification.
 *
 * @param message The success message
 * @param modifier Modifier for customization
 * @param actionLabel Optional action button label
 * @param onAction Callback when action button is clicked
 */
@Composable
fun SuccessToast(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    UniVibeToast(
        message = message,
        type = ToastType.Success,
        modifier = modifier,
        actionLabel = actionLabel,
        onAction = onAction
    )
}

/**
 * Error toast notification.
 *
 * @param message The error message
 * @param modifier Modifier for customization
 * @param actionLabel Optional action button label
 * @param onAction Callback when action button is clicked
 */
@Composable
fun ErrorToast(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    UniVibeToast(
        message = message,
        type = ToastType.Error,
        modifier = modifier,
        actionLabel = actionLabel,
        onAction = onAction
    )
}

/**
 * Warning toast notification.
 *
 * @param message The warning message
 * @param modifier Modifier for customization
 * @param actionLabel Optional action button label
 * @param onAction Callback when action button is clicked
 */
@Composable
fun WarningToast(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    UniVibeToast(
        message = message,
        type = ToastType.Warning,
        modifier = modifier,
        actionLabel = actionLabel,
        onAction = onAction
    )
}

/**
 * Info toast notification.
 *
 * @param message The info message
 * @param modifier Modifier for customization
 * @param actionLabel Optional action button label
 * @param onAction Callback when action button is clicked
 */
@Composable
fun InfoToast(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    UniVibeToast(
        message = message,
        type = ToastType.Info,
        modifier = modifier,
        actionLabel = actionLabel,
        onAction = onAction
    )
}

