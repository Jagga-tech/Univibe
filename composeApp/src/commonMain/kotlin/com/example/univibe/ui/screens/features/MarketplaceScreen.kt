package com.example.univibe.ui.screens.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.*
import com.example.univibe.ui.screens.detail.MarketplaceItemDetailScreen
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols

object MarketplaceScreen : Screen {
    @Composable
    override fun Content() {
        MarketplaceScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarketplaceScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var selectedCategory by remember { mutableStateOf<MarketplaceCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    
    val items = remember(selectedCategory, searchQuery) {
        var filtered = if (selectedCategory != null) {
            MockMarketplace.getItemsByCategory(selectedCategory!!)
        } else {
            MockMarketplace.getAvailableItems()
        }
        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter { item ->
                item.title.contains(searchQuery, ignoreCase = true) ||
                item.description.contains(searchQuery, ignoreCase = true)
            }
        }
        filtered
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
                    IconButton(onClick = { isSearching = !isSearching }) {
                        TextIcon(
                            symbol = UISymbols.SEARCH,
                            contentDescription = "Search",
                            fontSize = 20
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Navigate to create item screen */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                TextIcon(
                    symbol = UISymbols.ADD,
                    contentDescription = "Sell Item",
                    fontSize = 20
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
            contentPadding = PaddingValues(Dimensions.Spacing.default)
        ) {
            // Search Bar Section
            if (isSearching) {
                item {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search items...") },
                        leadingIcon = {
                            TextIcon(
                                symbol = UISymbols.SEARCH,
                                fontSize = 16
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { searchQuery = "" }
                                ) {
                                    TextIcon(
                                        symbol = UISymbols.CLOSE,
                                        fontSize = 16
                                    )
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimensions.Spacing.default),
                        singleLine = true
                    )
                }
            }
            
            item {
                CategoryFilterRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
            
            item {
                Text(
                    "${items.size} items available",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            if (items.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Spacing.xl),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
                        ) {
                            TextIcon(
                                symbol = UISymbols.SHOPPING_BAG,
                                fontSize = 48,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "No items found",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Be the first to list something!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(items) { item ->
                    MarketplaceItemCard(
                        item = item,
                        onClick = { navigator.push(MarketplaceItemDetailScreen(item.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterRow(
    selectedCategory: MarketplaceCategory?,
    onCategorySelected: (MarketplaceCategory?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
        contentPadding = PaddingValues(horizontal = Dimensions.Spacing.default)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }
        
        items(MarketplaceCategory.values().toList()) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text("${category.emoji} ${category.displayName}") }
            )
        }
    }
}

@Composable
private fun MarketplaceItemCard(
    item: MarketplaceItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(Dimensions.Spacing.default),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        item.category.emoji,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = item.condition.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(
                                horizontal = Dimensions.Spacing.xs,
                                vertical = 2.dp
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
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.padding(
                                    horizontal = Dimensions.Spacing.xs,
                                    vertical = 2.dp
                                )
                            )
                        }
                    }
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextIcon(
                        symbol = UISymbols.LOCATION,
                        fontSize = 12,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        item.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}