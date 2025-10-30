package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.SemanticColors

/**
 * Badge style types.
 */
enum class BadgeStyle {
    Filled,
    Outlined,
    Filled_Tonal
}

/**
 * Status badge displaying a status with color.
 *
 * Features:
 * - Multiple status types
 * - Customizable styling
 * - Compact size
 *
 * @param text Badge text
 * @param modifier Modifier for customization
 * @param backgroundColor Background color
 * @param textColor Text color
 * @param style Badge style
 */
@Composable
fun StatusBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    style: BadgeStyle = BadgeStyle.Filled
) {
    Box(
        modifier = modifier
            .then(
                when (style) {
                    BadgeStyle.Filled -> {
                        Modifier.background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                        )
                    }
                    BadgeStyle.Outlined -> {
                        Modifier
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                            )
                            .border(
                                width = 1.dp,
                                color = backgroundColor,
                                shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                            )
                    }
                    BadgeStyle.Filled_Tonal -> {
                        Modifier.background(
                            color = backgroundColor.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                        )
                    }
                }
            )
            .padding(horizontal = Dimensions.Spacing.sm, vertical = Dimensions.Spacing.xs),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (style == BadgeStyle.Outlined) backgroundColor else textColor,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 11.sp,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Notification badge (dot) with optional count.
 *
 * Features:
 * - Dot indicator
 * - Optional count display
 * - Status colors
 *
 * @param count Optional count to display (if > 0, shows count instead of dot)
 * @param modifier Modifier for customization
 * @param backgroundColor Background color
 */
@Composable
fun NotificationBadge(
    count: Int = 0,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.error
) {
    if (count > 0) {
        Badge(
            modifier = modifier,
            containerColor = backgroundColor,
            contentColor = Color.White
        ) {
            Text(
                text = if (count > 99) "99+" else count.toString(),
                fontSize = 9.sp,
                maxLines = 1
            )
        }
    } else {
        Box(
            modifier = modifier
                .size(8.dp)
                .background(backgroundColor, shape = CircleShape)
        )
    }
}

/**
 * Badged content (e.g., icon with notification count).
 *
 * Features:
 * - Icon with badge overlay
 * - Customizable badge count
 *
 * @param badgeCount Number to display in badge
 * @param modifier Modifier for customization
 * @param content The content to badge
 */
@Composable
fun ContentWithBadge(
    badgeCount: Int = 0,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BadgedBox(
        modifier = modifier,
        badge = {
            if (badgeCount > 0) {
                Badge(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color.White
                ) {
                    Text(
                        text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                        fontSize = 9.sp,
                        maxLines = 1
                    )
                }
            }
        }
    ) {
        content()
    }
}

/**
 * Online status indicator badge.
 *
 * @param isOnline Whether user is online
 * @param modifier Modifier for customization
 * @param size Size of the indicator
 */
@Composable
fun OnlineStatusBadge(
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 8.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = if (isOnline) SemanticColors.Online else SemanticColors.Offline,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = Color.White,
                shape = CircleShape
            )
    )
}

/**
 * Verified badge (checkmark).
 *
 * @param modifier Modifier for customization
 */
@Composable
fun VerifiedBadge(
    modifier: Modifier = Modifier
) {
    StatusBadge(
        text = "✓ Verified",
        modifier = modifier,
        backgroundColor = SemanticColors.Verified,
        textColor = MaterialTheme.colorScheme.surface
    )
}

/**
 * Premium badge.
 *
 * @param modifier Modifier for customization
 */
@Composable
fun PremiumBadge(
    modifier: Modifier = Modifier
) {
    StatusBadge(
        text = "★ Premium",
        modifier = modifier,
        backgroundColor = SemanticColors.Premium,
        textColor = MaterialTheme.colorScheme.surface
    )
}