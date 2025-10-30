package com.example.univibe.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.theme.Dimensions

/**
 * Linear progress bar with optional label and percentage.
 *
 * Features:
 * - Smooth animation
 * - Optional label display
 * - Optional percentage display
 * - Customizable colors
 * - Customizable height
 *
 * @param progress Progress value (0f to 1f)
 * @param modifier Modifier for customization
 * @param label Optional label text
 * @param showPercentage Whether to show percentage
 * @param height Height of the progress bar
 * @param backgroundColor Background color
 * @param progressColor Progress color
 */
@Composable
fun UniVibeProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    label: String? = null,
    showPercentage: Boolean = false,
    height: Dp = 4.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressColor: Color = MaterialTheme.colorScheme.primary
) {
    val normalizedProgress = progress.coerceIn(0f, 1f)
    
    Column(modifier = modifier) {
        // Label and percentage row
        if (!label.isNullOrEmpty() || showPercentage) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.xs),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!label.isNullOrEmpty()) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (showPercentage) {
                    Text(
                        text = "${(normalizedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Progress bar
        LinearProgressIndicator(
            progress = { normalizedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(RoundedCornerShape(Dimensions.CornerRadius.full)),
            color = progressColor,
            trackColor = backgroundColor,
            drawStopIndicator = { }
        )
    }
}

/**
 * Circular progress bar component for displaying progress in a circle.
 *
 * Features:
 * - Animated progress ring
 * - Center percentage text
 * - Customizable colors and size
 *
 * @param progress Progress value (0f to 1f)
 * @param modifier Modifier for customization
 * @param size Size of the circular progress
 * @param label Optional label text at center
 * @param showPercentage Whether to show percentage at center
 * @param strokeWidth Width of the progress ring
 * @param backgroundColor Background ring color
 * @param progressColor Progress color
 */
@Composable
fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    label: String? = null,
    showPercentage: Boolean = false,
    strokeWidth: Dp = 4.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    progressColor: Color = MaterialTheme.colorScheme.primary
) {
    val normalizedProgress = progress.coerceIn(0f, 1f)
    
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        Box(
            modifier = Modifier
                .size(size)
                .background(backgroundColor.copy(alpha = 0.2f), shape = RoundedCornerShape(50))
        )
        
        // Progress circle (simplified - using Material3 circular progress)
        androidx.compose.material3.CircularProgressIndicator(
            progress = { normalizedProgress },
            modifier = Modifier.size(size),
            strokeWidth = strokeWidth,
            color = progressColor,
            trackColor = backgroundColor.copy(alpha = 0.2f)
        )
        
        // Center content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showPercentage) {
                Text(
                    text = "${(normalizedProgress * 100).toInt()}%",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
            
            if (!label.isNullOrEmpty()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp
                )
            }
        }
    }
}

/**
 * Stacked progress bar showing multiple progress values.
 *
 * Features:
 * - Multiple progress segments
 * - Each segment with custom color
 * - Smooth transitions
 *
 * @param segments List of progress values with colors
 * @param modifier Modifier for customization
 * @param height Height of the progress bar
 */
@Composable
fun StackedProgressBar(
    segments: List<Pair<Float, Color>>,
    modifier: Modifier = Modifier,
    height: Dp = 6.dp
) {
    val totalProgress = segments.sumOf { it.first.toDouble() }.toFloat().coerceIn(0f, 1f)
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(Dimensions.CornerRadius.full))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        segments.forEach { (progress, color) ->
            val width = (progress / totalProgress) * 100
            if (width > 0) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(width / 100)
                        .background(color)
                )
            }
        }
    }
}

/**
 * Stepped progress bar showing discrete steps.
 *
 * Features:
 * - Step-by-step progress
 * - Customizable step count
 * - Current step highlighting
 *
 * @param currentStep Current step (0-based)
 * @param totalSteps Total number of steps
 * @param modifier Modifier for customization
 * @param steps Optional list of step labels
 */
@Composable
fun SteppedProgressBar(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    steps: List<String>? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Step indicators
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            repeat(totalSteps) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(Dimensions.CornerRadius.full))
                        .background(
                            if (index <= currentStep) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        )
                )
            }
        }
        
        // Step labels
        if (steps != null && steps.size == totalSteps) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                steps.forEachIndexed { index, label ->
                    Text(
                        text = label,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (index <= currentStep) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}