package com.example.univibe.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.animations.AnimationConstants
import com.example.univibe.ui.theme.Dimensions

/**
 * Icon button size variants.
 */
enum class IconButtonSize {
    Small,    // 32dp
    Medium,   // 40dp
    Large     // 48dp
}

/**
 * Icon button variant types.
 */
enum class IconButtonVariant {
    Filled,
    FilledTonal,
    Outlined,
    Standard
}

/**
 * Get size for icon button size variant.
 */
fun IconButtonSize.size(): Dp = when (this) {
    IconButtonSize.Small -> 32.dp
    IconButtonSize.Medium -> 40.dp
    IconButtonSize.Large -> 48.dp
}

/**
 * Get icon size for button size variant.
 */
fun IconButtonSize.iconSize(): Dp = when (this) {
    IconButtonSize.Small -> Dimensions.IconSize.sm
    IconButtonSize.Medium -> Dimensions.IconSize.md
    IconButtonSize.Large -> Dimensions.IconSize.lg
}

/**
 * Enhanced icon button component for UniVibe with multiple variants and sizes.
 *
 * Features:
 * - Multiple variants (Filled, FilledTonal, Outlined, Standard)
 * - Three sizes (Small, Medium, Large)
 * - Loading state support
 * - Scale animation on press
 * - Full accessibility support
 *
 * @param icon Icon to display
 * @param onClick Click handler
 * @param modifier Modifier for customization
 * @param variant Button variant
 * @param size Button size
 * @param enabled Whether button is enabled
 * @param loading Whether button is in loading state
 * @param contentDescription Description for accessibility
 */
@Composable
fun UniVibeIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: IconButtonVariant = IconButtonVariant.Standard,
    size: IconButtonSize = IconButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    contentDescription: String? = null
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release -> isPressed = false
                is PressInteraction.Cancel -> isPressed = false
            }
        }
    }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "iconButtonScale"
    )
    
    val buttonSize = size.size()
    val iconSize = size.iconSize()
    
    Box(
        modifier = modifier
            .size(buttonSize)
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        when (variant) {
            IconButtonVariant.Filled -> {
                IconButton(
                    onClick = onClick,
                    enabled = enabled && !loading,
                    modifier = Modifier
                        .size(buttonSize)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    interactionSource = interactionSource
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(iconSize),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = icon,
                            contentDescription = contentDescription,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
            
            IconButtonVariant.FilledTonal -> {
                IconButton(
                    onClick = onClick,
                    enabled = enabled && !loading,
                    modifier = Modifier
                        .size(buttonSize)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    interactionSource = interactionSource
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(iconSize),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = icon,
                            contentDescription = contentDescription,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
            
            IconButtonVariant.Outlined -> {
                IconButton(
                    onClick = onClick,
                    enabled = enabled && !loading,
                    modifier = Modifier
                        .size(buttonSize)
                        .clip(CircleShape),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    interactionSource = interactionSource
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(iconSize),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = icon,
                            contentDescription = contentDescription,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
            
            IconButtonVariant.Standard -> {
                IconButton(
                    onClick = onClick,
                    enabled = enabled && !loading,
                    modifier = Modifier.size(buttonSize),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    interactionSource = interactionSource
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(iconSize),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = icon,
                            contentDescription = contentDescription,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Floating Action Button (FAB) component for UniVibe.
 *
 * Features:
 * - Multiple sizes (Small, Medium, Large)
 * - Loading state support
 * - Scale animation on press
 * - Extended FAB with label
 *
 * @param icon Icon to display
 * @param onClick Click handler
 * @param modifier Modifier for customization
 * @param size FAB size
 * @param enabled Whether FAB is enabled
 * @param loading Whether FAB is in loading state
 * @param contentDescription Description for accessibility
 * @param label Optional label for extended FAB
 */
@Composable
fun UniVibeFAB(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: IconButtonSize = IconButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    contentDescription: String? = null,
    label: String? = null
) {
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> isPressed = true
                is PressInteraction.Release -> isPressed = false
                is PressInteraction.Cancel -> isPressed = false
            }
        }
    }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.95f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "fabScale"
    )
    
    val iconSize = size.iconSize()
    
    if (!label.isNullOrEmpty()) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            icon = {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(iconSize),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        modifier = Modifier.size(iconSize)
                    )
                }
            },
            text = { Text(label) },
            modifier = modifier.scale(scale),
            enabled = enabled && !loading,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            interactionSource = interactionSource
        )
    } else {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier.scale(scale),
            enabled = enabled && !loading,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            interactionSource = interactionSource
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(iconSize),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}