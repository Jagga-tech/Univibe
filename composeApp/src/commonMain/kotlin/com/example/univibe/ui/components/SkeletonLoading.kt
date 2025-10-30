package com.example.univibe.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions

/**
 * Skeleton loading composables for content placeholders with shimmer effect.
 */
object SkeletonLoading {
    
    /**
     * Shimmer brush animation for skeleton screens.
     */
    @Composable
    fun shimmerBrush(): Brush {
        val transition = rememberInfiniteTransition(label = "shimmer")
        val translateAnim by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "shimmerTranslate"
        )
        
        return Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
            ),
            start = Offset(translateAnim - 1000f, 0f),
            end = Offset(translateAnim, 0f)
        )
    }
    
    /**
     * Skeleton post card with shimmer effect.
     */
    @Composable
    fun PostCardSkeleton() {
        val brush = shimmerBrush()
        
        UniVibeCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            elevation = 3.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.default)
            ) {
                // Header skeleton
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        // Avatar skeleton
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(brush)
                        )
                        
                        Column(modifier = Modifier.weight(1f)) {
                            // Name skeleton
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(brush)
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Timestamp skeleton
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(brush)
                            )
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(brush)
                    )
                }
                
                Spacer(modifier = Modifier.height(Dimensions.Spacing.default))
                
                // Content skeleton (3 lines)
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(if (it == 2) 0.8f else 1f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                Spacer(modifier = Modifier.height(Dimensions.Spacing.default))
                
                // Engagement skeleton
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                }
            }
        }
    }
    
    /**
     * Skeleton user profile with shimmer effect.
     */
    @Composable
    fun UserProfileSkeleton() {
        val brush = shimmerBrush()
        
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header background skeleton
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(brush)
            )
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.default),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar skeleton
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(brush)
                            .offset(y = (-40).dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Name skeleton
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Bio skeleton
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Stats skeleton
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimensions.Spacing.lg),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(brush)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .width(60.dp)
                                        .height(10.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(brush)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Skeleton message card with shimmer effect.
     */
    @Composable
    fun MessageCardSkeleton() {
        val brush = shimmerBrush()
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
        ) {
            // Avatar skeleton
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(brush)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                // Name skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Message skeleton
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush)
                )
            }
            
            // Timestamp skeleton
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(10.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
        }
    }
}