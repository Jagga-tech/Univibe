package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions

/**
 * Simple list item component with title and optional subtitle.
 *
 * Features:
 * - Title and subtitle display
 * - Leading and trailing content
 * - Clickable with ripple effect
 * - Customizable spacing and styling
 *
 * @param title Title text
 * @param onClick Callback when item is clicked
 * @param modifier Modifier for customization
 * @param subtitle Optional subtitle text
 * @param leadingIcon Optional leading icon
 * @param trailingIcon Optional trailing icon
 * @param enabled Whether item is enabled/clickable
 */
@Composable
fun UniVibeListItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .padding(horizontal = Dimensions.Spacing.default, vertical = Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        // Leading icon
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.IconSize.md),
                tint = if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                }
            )
        }
        
        // Title and subtitle
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (enabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            if (!subtitle.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        // Trailing icon
        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.IconSize.md),
                tint = if (enabled) {
                    MaterialTheme.colorScheme.onSurfaceVariant
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                }
            )
        }
    }
}

/**
 * Switch list item with title and toggle.
 *
 * @param title Title text
 * @param checked Whether switch is checked
 * @param onCheckedChange Callback when switch state changes
 * @param modifier Modifier for customization
 * @param subtitle Optional subtitle text
 * @param leadingIcon Optional leading icon
 * @param enabled Whether item is enabled
 */
@Composable
fun SwitchListItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = { onCheckedChange(!checked) },
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            )
            .padding(horizontal = Dimensions.Spacing.default, vertical = Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        // Leading icon
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.IconSize.md),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Title and subtitle
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            if (!subtitle.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        // Switch
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

/**
 * Divider list item for section separation.
 *
 * @param modifier Modifier for customization
 * @param label Optional label for the divider
 */
@Composable
fun DividerListItem(
    modifier: Modifier = Modifier,
    label: String? = null
) {
    if (!label.isNullOrEmpty()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimensions.Spacing.default,
                    vertical = Dimensions.Spacing.md
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
        ) {
            Divider(modifier = Modifier.weight(1f))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Divider(modifier = Modifier.weight(1f))
        }
    } else {
        Divider(modifier = modifier)
    }
}