package com.example.univibe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * UniVibe Filter Chip
 *
 * Selectable chip for filtering with check icon when selected.
 * Uses Material 3 FilterChip with UniVibe styling.
 *
 * **Example Usage:**
 * ```kotlin
 * var selected by remember { mutableStateOf(false) }
 * UniVibeFilterChip(
 *     label = "Tech",
 *     selected = selected,
 *     onSelectedChange = { selected = it }
 * )
 * ```
 *
 * @param label Chip text to display.
 * @param selected Whether the chip is currently selected.
 * @param onSelectedChange Callback when selection state changes.
 * @param modifier Modifier to apply to the chip.
 * @param enabled Whether the chip is enabled for interaction.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniVibeFilterChip(
    label: String,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                TextIcon(
                    symbol = UISymbols.CHECK,
                    contentDescription = "Selected",
                    fontSize = 18
                )
            }
        } else null,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

/**
 * UniVibe Input Chip
 *
 * Chip with dismiss/remove action, typically used for displaying selected items.
 * Uses Material 3 InputChip with UniVibe styling and X button for dismissal.
 *
 * **Example Usage:**
 * ```kotlin
 * UniVibeInputChip(
 *     label = "Tech",
 *     onDismiss = { /* remove item */ }
 * )
 * ```
 *
 * @param label Chip text to display.
 * @param onDismiss Callback when the chip is dismissed (X button clicked).
 * @param modifier Modifier to apply to the chip.
 * @param enabled Whether the chip is enabled for interaction.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniVibeInputChip(
    label: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    InputChip(
        selected = true,
        onClick = {},
        label = { Text(label) },
        modifier = modifier,
        enabled = enabled,
        trailingIcon = {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(Dimensions.IconSize.small)
            ) {
                TextIcon(
                    symbol = UISymbols.CLOSE,
                    contentDescription = "Remove",
                    fontSize = 18
                )
            }
        },
        colors = InputChipDefaults.inputChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

/**
 * Generic Chip Group Component
 *
 * Horizontal scrolling row of filter chips for any data type.
 * Automatically generates chip labels using itemLabel function.
 *
 * **Example Usages:**
 * ```kotlin
 * // With String list
 * var selected by remember { mutableStateOf<Set<String>>(emptySet()) }
 * ChipGroup(
 *     items = listOf("Tech", "Gaming", "Sports"),
 *     selectedItems = selected,
 *     onItemClick = { chip -> selected = if (chip in selected) selected - chip else selected + chip }
 * )
 *
 * // With custom data class
 * data class Category(val id: String, val name: String)
 * var selectedCategories by remember { mutableStateOf<Set<Category>>(emptySet()) }
 * ChipGroup(
 *     items = categories,
 *     selectedItems = selectedCategories,
 *     onItemClick = { cat -> /* update selection */ },
 *     itemLabel = { it.name }
 * )
 * ```
 *
 * @param items List of items to display as chips.
 * @param selectedItems Set of currently selected items.
 * @param onItemClick Callback when a chip is clicked.
 * @param modifier Modifier to apply to the row.
 * @param itemLabel Function to extract display label from item (defaults to toString()).
 */
@Composable
fun <T> ChipGroup(
    items: List<T>,
    selectedItems: Set<T> = emptySet(),
    onItemClick: (T) -> Unit = {},
    modifier: Modifier = Modifier,
    itemLabel: (T) -> String = { it.toString() }
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
        contentPadding = PaddingValues(horizontal = Dimensions.Spacing.default)
    ) {
        items(items) { item ->
            UniVibeFilterChip(
                label = itemLabel(item),
                selected = selectedItems.contains(item),
                onSelectedChange = { onItemClick(item) }
            )
        }
    }
}