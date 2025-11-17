package com.example.univibe.ui.design

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * UniVibe Design System - Single source of truth for design tokens
 * Professional, consistent design language for all screens
 */
object UniVibeDesign {
    
    // ═══════════════════════════════════════════════════════════════
    // SPACING SYSTEM - Mathematical progression for visual harmony
    // ═══════════════════════════════════════════════════════════════
    object Spacing {
        val xxxs = 2.dp    // Micro spacing
        val xxs = 4.dp     // Tiny spacing
        val xs = 8.dp      // Small spacing
        val sm = 12.dp     // Medium-small spacing
        val md = 16.dp     // Standard spacing
        val lg = 24.dp     // Large spacing
        val xl = 32.dp     // Extra large spacing
        val xxl = 48.dp    // Double extra large
        val xxxl = 64.dp   // Triple extra large
        val huge = 96.dp   // Massive spacing
        
        // Semantic spacing for consistent application
        val screenPadding = md          // Standard screen edge padding
        val cardPadding = md            // Card internal padding
        val sectionSpacing = lg         // Between major sections
        val itemSpacing = sm            // Between list items
        val elementSpacing = xs         // Between UI elements
        val buttonSpacing = sm          // Between buttons
    }
    
    // ═══════════════════════════════════════════════════════════════
    // CARD SYSTEM - Unified card styling
    // ═══════════════════════════════════════════════════════════════
    object Cards {
        val defaultElevation = 2.dp
        val hoverElevation = 4.dp
        val pressedElevation = 1.dp
        val defaultRadius = 12.dp
        val largeRadius = 16.dp
        val smallRadius = 8.dp
        
        @Composable
        fun standardColors() = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        
        @Composable
        fun elevatedColors() = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
        
        @Composable
        fun standardElevation() = CardDefaults.cardElevation(
            defaultElevation = defaultElevation,
            pressedElevation = pressedElevation,
            hoveredElevation = hoverElevation
        )
        
        val standardShape = RoundedCornerShape(defaultRadius)
        val largeShape = RoundedCornerShape(largeRadius)
        val smallShape = RoundedCornerShape(smallRadius)
    }
    
    // ═══════════════════════════════════════════════════════════════
    // TYPOGRAPHY SCALE - Hierarchical text system
    // ═══════════════════════════════════════════════════════════════
    object Text {
        @Composable
        fun screenTitle() = MaterialTheme.typography.headlineLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        @Composable
        fun screenSubtitle() = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        @Composable
        fun sectionTitle() = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        @Composable
        fun cardTitle() = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        @Composable
        fun body() = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
        
        @Composable
        fun bodySecondary() = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        @Composable
        fun caption() = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        @Composable
        fun buttonText() = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.SemiBold
        )
        
        @Composable
        fun overline() = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    
    // ═══════════════════════════════════════════════════════════════
    // BUTTON SYSTEM - Consistent interactive elements
    // ═══════════════════════════════════════════════════════════════
    object Buttons {
        val standardHeight = 48.dp
        val largeHeight = 56.dp
        val smallHeight = 36.dp
        
        val standardShape = RoundedCornerShape(12.dp)
        val pillShape = RoundedCornerShape(50)
        
        @Composable
        fun primaryColors() = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
        
        @Composable
        fun secondaryColors() = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
        
        @Composable
        fun outlinedColors() = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
    
    // ═══════════════════════════════════════════════════════════════
    // SCREEN LAYOUTS - Consistent screen patterns
    // ═══════════════════════════════════════════════════════════════
    
    /**
     * Standard screen layout with consistent padding and structure
     */
    @Composable
    fun StandardScreen(
        modifier: Modifier = Modifier,
        title: String? = null,
        subtitle: String? = null,
        topBar: @Composable () -> Unit = {},
        floatingActionButton: @Composable () -> Unit = {},
        bottomBar: @Composable () -> Unit = {},
        content: @Composable ColumnScope.() -> Unit
    ) {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            floatingActionButton = floatingActionButton,
            bottomBar = bottomBar
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Spacing.screenPadding),
                verticalArrangement = Arrangement.spacedBy(Spacing.sectionSpacing)
            ) {
                // Screen header
                if (title != null || subtitle != null) {
                    ScreenHeader(title = title, subtitle = subtitle)
                }
                
                content()
            }
        }
    }
    
    /**
     * Screen header with title and optional subtitle
     */
    @Composable
    private fun ScreenHeader(
        title: String?,
        subtitle: String?
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            title?.let {
                Text(
                    text = it,
                    style = Text.screenTitle()
                )
            }
            subtitle?.let {
                Text(
                    text = it,
                    style = Text.bodySecondary()
                )
            }
        }
    }
    
    /**
     * Content section with optional title and action
     */
    @Composable
    fun Section(
        title: String? = null,
        titleAction: @Composable (() -> Unit)? = null,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            if (title != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = Text.sectionTitle()
                    )
                    titleAction?.invoke()
                }
            }
            content()
        }
    }
    
    /**
     * Standard card layout
     */
    @Composable
    fun StandardCard(
        modifier: Modifier = Modifier,
        onClick: (() -> Unit)? = null,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Card(
            modifier = modifier,
            onClick = onClick ?: {},
            colors = Cards.standardColors(),
            elevation = Cards.standardElevation(),
            shape = Cards.standardShape
        ) {
            Column(
                modifier = Modifier.padding(Spacing.cardPadding),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm),
                content = content
            )
        }
    }
    
    /**
     * List item card - optimized for list displays
     */
    @Composable
    fun ListItemCard(
        modifier: Modifier = Modifier,
        onClick: (() -> Unit)? = null,
        leading: @Composable (() -> Unit)? = null,
        trailing: @Composable (() -> Unit)? = null,
        overline: String? = null,
        title: String,
        subtitle: String? = null,
        description: String? = null
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            onClick = onClick ?: {},
            colors = Cards.standardColors(),
            elevation = Cards.standardElevation(),
            shape = Cards.standardShape
        ) {
            Row(
                modifier = Modifier.padding(Spacing.cardPadding),
                horizontalArrangement = Arrangement.spacedBy(Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leading content (avatar, icon, etc.)
                leading?.invoke()
                
                // Main content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.xxs)
                ) {
                    overline?.let {
                        Text(
                            text = it,
                            style = Text.overline()
                        )
                    }
                    
                    Text(
                        text = title,
                        style = Text.cardTitle()
                    )
                    
                    subtitle?.let {
                        Text(
                            text = it,
                            style = Text.bodySecondary()
                        )
                    }
                    
                    description?.let {
                        Text(
                            text = it,
                            style = Text.caption()
                        )
                    }
                }
                
                // Trailing content (actions, badges, etc.)
                trailing?.invoke()
            }
        }
    }
    
    /**
     * Empty state component
     */
    @Composable
    fun EmptyState(
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        title: String,
        description: String? = null,
        action: @Composable (() -> Unit)? = null
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            icon()
            
            Text(
                text = title,
                style = Text.sectionTitle(),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            description?.let {
                Text(
                    text = it,
                    style = Text.bodySecondary()
                )
            }
            
            action?.invoke()
        }
    }
    
    /**
     * Loading state component
     */
    @Composable
    fun LoadingState(
        modifier: Modifier = Modifier,
        message: String = "Loading..."
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = message,
                style = Text.bodySecondary()
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// DESIGN SYSTEM EXTENSIONS
// ═══════════════════════════════════════════════════════════════

/**
 * Extension for applying standard card modifiers
 */
fun Modifier.standardCard(): Modifier = this.fillMaxWidth()

/**
 * Extension for applying standard button modifiers
 */
fun Modifier.standardButton(): Modifier = this
    .fillMaxWidth()
    .height(UniVibeDesign.Buttons.standardHeight)

/**
 * Extension for applying standard list item modifiers
 */
fun Modifier.standardListItem(): Modifier = this.fillMaxWidth()