package com.example.univibe.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.animations.AnimationConstants
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Button size variants for consistency.
 */
enum class ButtonSize {
    Small,    // 40dp height
    Medium,   // 48dp height (default)
    Large     // 56dp height
}

/**
 * Get height for button size variant.
 */
fun ButtonSize.height(): Dp = when (this) {
    ButtonSize.Small -> 40.dp
    ButtonSize.Medium -> 48.dp
    ButtonSize.Large -> 56.dp
}

/**
 * Enhanced primary button variant for UniVibe with animations and variants.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    leadingIcon: String? = null,
    trailingIcon: String? = null
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
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "primaryButtonScale"
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .height(size.height())
            .scale(scale)
            .animateContentSize(),
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        interactionSource = interactionSource
    ) {
        if (loading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.IconSize.sm),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Text("Loading...")
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                if (leadingIcon != null) {
                    TextIcon(
                        symbol = leadingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
                Text(text)
                if (trailingIcon != null) {
                    TextIcon(
                        symbol = trailingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
            }
        }
    }
}

/**
 * Secondary button variant with animation support.
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    leadingIcon: String? = null,
    trailingIcon: String? = null
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
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "secondaryButtonScale"
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .height(size.height())
            .scale(scale)
            .animateContentSize(),
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        interactionSource = interactionSource
    ) {
        if (loading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.IconSize.sm),
                    color = MaterialTheme.colorScheme.onSecondary,
                    strokeWidth = 2.dp
                )
                Text("Loading...")
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                if (leadingIcon != null) {
                    TextIcon(
                        symbol = leadingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
                Text(text)
                if (trailingIcon != null) {
                    TextIcon(
                        symbol = trailingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
            }
        }
    }
}

/**
 * Tertiary button variant with animation support.
 */
@Composable
fun TertiaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    leadingIcon: String? = null,
    trailingIcon: String? = null
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
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "tertiaryButtonScale"
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .height(size.height())
            .scale(scale)
            .animateContentSize(),
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        interactionSource = interactionSource
    ) {
        if (loading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.IconSize.sm),
                    color = MaterialTheme.colorScheme.onTertiary,
                    strokeWidth = 2.dp
                )
                Text("Loading...")
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                if (leadingIcon != null) {
                    TextIcon(
                        symbol = leadingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
                Text(text)
                if (trailingIcon != null) {
                    TextIcon(
                        symbol = trailingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
            }
        }
    }
}

/**
 * Outline button variant with animation support.
 */
@Composable
fun OutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    leadingIcon: String? = null,
    trailingIcon: String? = null
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
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "outlineButtonScale"
    )
    
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(size.height())
            .scale(scale)
            .animateContentSize(),
        enabled = enabled && !loading,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        interactionSource = interactionSource
    ) {
        if (loading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.IconSize.sm),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
                Text("Loading...")
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                if (leadingIcon != null) {
                    TextIcon(
                        symbol = leadingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
                Text(text)
                if (trailingIcon != null) {
                    TextIcon(
                        symbol = trailingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
            }
        }
    }
}

/**
 * Text button variant for low emphasis actions.
 */
@Composable
fun TextButtonVariant(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    size: ButtonSize = ButtonSize.Medium,
    leadingIcon: String? = null,
    trailingIcon: String? = null
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
        targetValue = if (isPressed && enabled) 0.98f else 1f,
        animationSpec = AnimationConstants.quickSpec,
        label = "textButtonScale"
    )
    
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(size.height())
            .scale(scale)
            .animateContentSize(),
        enabled = enabled && !loading,
        interactionSource = interactionSource
    ) {
        if (loading) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimensions.IconSize.sm),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
                Text("Loading...")
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                if (leadingIcon != null) {
                    TextIcon(
                        symbol = leadingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
                Text(text)
                if (trailingIcon != null) {
                    TextIcon(
                        symbol = trailingIcon,
                        contentDescription = null,
                        fontSize = 14
                    )
                }
            }
        }
    }
}