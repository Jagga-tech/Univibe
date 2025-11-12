package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.*
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.util.ShareHelper

data class MarketplaceItemDetailScreen(val itemId: String) : Screen {
    @Composable
    override fun Content() {
        MarketplaceItemDetailScreenContent(itemId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarketplaceItemDetailScreenContent(itemId: String) {
    val navigator = LocalNavigator.currentOrThrow
    val item = remember { MockMarketplace.getItemById(itemId) }
    
    if (item == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Item Not Found") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            TextIcon(
                                symbol = UISymbols.BACK,
                                contentDescription = "Back",
                                fontSize = 20
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Item not found")
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marketplace") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.BACK,
                            contentDescription = "Back",
                            fontSize = 20
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        val shareText = ShareHelper.shareMarketplaceItem(item)
                        println("Share: $shareText")
                    }) {
                        TextIcon(
                            symbol = UISymbols.SHARE,
                            contentDescription = "Share",
                            fontSize = 20
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 2.dp
            ) {
                Button(
                    onClick = { /* TODO: Navigate to chat with seller */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.default),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        PlatformIcons.ChatBubble,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(Dimensions.Spacing.sm))
                    Text("Contact Seller")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Dimensions.Spacing.default),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.lg)
        ) {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.large
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            item.category.emoji,
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
            }
            
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    
                    Text(
                        text = "$${item.price}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = item.condition.displayName,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(
                                    horizontal = Dimensions.Spacing.sm,
                                    vertical = Dimensions.Spacing.xs
                                )
                            )
                        }
                        
                        if (item.isNegotiable) {
                            Surface(
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = "Negotiable",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.padding(
                                        horizontal = Dimensions.Spacing.sm,
                                        vertical = Dimensions.Spacing.xs
                                    )
                                )
                            }
                        }
                    }
                }
            }
            
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                    Text(
                        "Seller",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            /* TODO: Navigate to seller profile */
                        }
                    ) {
                        Surface(
                            modifier = Modifier.size(Dimensions.Spacing.lg),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.extraLarge
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    item.seller.fullName.firstOrNull()?.uppercase() ?: "?",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                        Column {
                            Text(
                                text = item.seller.fullName,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = item.seller.major ?: "Student",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                    Text(
                        "Description",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)) {
                    Text(
                        "Details",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    ItemDetailRow(
                        icon = PlatformIcons.Tag,
                        label = "Category",
                        value = item.category.displayName
                    )
                    
                    ItemDetailRow(
                        icon = PlatformIcons.Place,
                        label = "Location",
                        value = item.location
                    )
                }
            }
            
            if (item.tags.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                        Text(
                            "Tags",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            item.tags.forEach { tag ->
                                SuggestionChip(
                                    onClick = { /* TODO: Filter by tag */ },
                                    label = { Text("#$tag", style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}