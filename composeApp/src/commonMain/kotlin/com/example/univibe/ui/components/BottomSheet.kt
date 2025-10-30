package com.example.univibe.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions

/**
 * UniVibe Modal Bottom Sheet component.
 *
 * Features:
 * - Swipe to dismiss
 * - Header with close button
 * - Scrollable content
 * - Material 3 compliant
 *
 * @param isOpen Whether the bottom sheet is visible
 * @param onDismiss Callback when bottom sheet is dismissed
 * @param title Optional title for the bottom sheet
 * @param content The content to display in the bottom sheet
 */
@Composable
fun UniVibeBottomSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()
    
    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            scrimColor = androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.32f),
            shape = RoundedCornerShape(
                topStart = Dimensions.CornerRadius.large,
                topEnd = Dimensions.CornerRadius.large
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.default)
            ) {
                // Handle bar
                Box(
                    modifier = Modifier
                        .padding(top = Dimensions.Spacing.md)
                        .align(Alignment.CenterHorizontally)
                        .size(
                            width = 40.dp,
                            height = 4.dp
                        )
                        .background(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                        )
                )
                
                // Title
                if (!title.isNullOrEmpty()) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(
                            horizontal = Dimensions.Spacing.default,
                            vertical = Dimensions.Spacing.md
                        )
                    )
                    
                    Divider(modifier = Modifier.padding(horizontal = Dimensions.Spacing.default))
                }
                
                // Content
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
                content()
            }
        }
    }
}

/**
 * Bottom sheet action item for menu-style bottom sheets.
 *
 * @param label Text to display
 * @param onClick Callback when item is clicked
 * @param modifier Modifier for customization
 * @param leadingIcon Optional leading icon
 * @param isDangerous Whether this is a dangerous action (red text)
 */
@Composable
fun BottomSheetAction(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    isDangerous: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() }
            .padding(
                horizontal = Dimensions.Spacing.default,
                vertical = Dimensions.Spacing.md
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isDangerous) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Bottom sheet confirmation dialog with title, message, and action buttons.
 *
 * @param isOpen Whether the bottom sheet is visible
 * @param onDismiss Callback when sheet is dismissed
 * @param title Title of the sheet
 * @param message Message content
 * @param actionLabel Label for the primary action button
 * @param onAction Callback when primary action is clicked
 * @param cancelLabel Label for the cancel button (optional)
 * @param onCancel Callback when cancel is clicked (optional)
 */
@Composable
fun BottomSheetConfirmation(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    actionLabel: String,
    onAction: () -> Unit,
    cancelLabel: String? = null,
    onCancel: (() -> Unit)? = null
) {
    UniVibeBottomSheet(
        isOpen = isOpen,
        onDismiss = onDismiss,
        title = title
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.Spacing.default)
        ) {
            // Message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = Dimensions.Spacing.lg)
            )
            
            // Action buttons
            PrimaryButton(
                text = actionLabel,
                onClick = {
                    onAction()
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.md)
            )
            
            if (!cancelLabel.isNullOrEmpty()) {
                OutlineButton(
                    text = cancelLabel,
                    onClick = {
                        onCancel?.invoke()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

