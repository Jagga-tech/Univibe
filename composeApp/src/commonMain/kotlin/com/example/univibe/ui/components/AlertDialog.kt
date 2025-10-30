package com.example.univibe.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * UniVibe Alert Dialog component for confirmations and alerts.
 *
 * Features:
 * - Icon support
 * - Title and message display
 * - Customizable buttons
 * - Full Material 3 support
 *
 * @param onDismiss Callback when dialog is dismissed
 * @param title Title of the dialog
 * @param message Message/content of the dialog
 * @param confirmText Text for confirm/primary button
 * @param onConfirm Callback when confirm button is clicked
 * @param dismissText Optional text for dismiss button
 * @param onDismissText Callback when dismiss button is clicked (optional)
 * @param icon Optional icon to display
 * @param modifier Modifier for customization
 * @param isDangerous Whether this is a dangerous action (red confirm button)
 */
@Composable
fun UniVibeAlertDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    dismissText: String? = null,
    onDismissText: (() -> Unit)? = null,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    isDangerous: Boolean = false
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        icon = if (icon != null) {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            null
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text(
                    confirmText,
                    color = if (isDangerous) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                )
            }
        },
        dismissButton = if (!dismissText.isNullOrEmpty()) {
            {
                TextButton(onClick = {
                    onDismissText?.invoke()
                    onDismiss()
                }) {
                    Text(
                        dismissText,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        } else {
            null
        }
    )
}

/**
 * Simplified confirmation dialog (Yes/No).
 *
 * @param onDismiss Callback when dialog is dismissed
 * @param title Title of the dialog
 * @param message Message/content of the dialog
 * @param onYes Callback when "Yes" button is clicked
 * @param onNo Callback when "No" button is clicked
 * @param icon Optional icon to display
 * @param isDangerous Whether this is a dangerous action
 */
@Composable
fun ConfirmationDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    onYes: () -> Unit,
    onNo: (() -> Unit)? = null,
    icon: ImageVector? = null,
    isDangerous: Boolean = false
) {
    UniVibeAlertDialog(
        onDismiss = onDismiss,
        title = title,
        message = message,
        confirmText = "Yes",
        onConfirm = onYes,
        dismissText = "No",
        onDismissText = onNo,
        icon = icon,
        isDangerous = isDangerous
    )
}

/**
 * Simple info dialog (OK button only).
 *
 * @param onDismiss Callback when dialog is dismissed
 * @param title Title of the dialog
 * @param message Message/content of the dialog
 * @param icon Optional icon to display
 */
@Composable
fun InfoDialog(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    icon: ImageVector? = null
) {
    UniVibeAlertDialog(
        onDismiss = onDismiss,
        title = title,
        message = message,
        confirmText = "OK",
        onConfirm = { },
        icon = icon
    )
}