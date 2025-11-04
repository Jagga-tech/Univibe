package com.example.univibe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Search input field with clear button and leading search icon.
 *
 * Features:
 * - Leading search icon
 * - Trailing clear button (shows when text is not empty)
 * - Error state with message display
 * - Full accessibility support
 * - Optimized for search queries
 *
 * @param value Current search value
 * @param onValueChange Callback when value changes
 * @param modifier Modifier for customization
 * @param placeholder Optional placeholder text (defaults to "Search...")
 * @param isError Whether field is in error state
 * @param errorMessage Error message to display
 * @param enabled Whether field is enabled
 * @param onClear Callback when clear button is clicked (optional)
 */
@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    isError: Boolean = false,
    errorMessage: String = "",
    enabled: Boolean = true,
    onClear: (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.InputHeight),
            placeholder = { Text(placeholder) },
            leadingIcon = {
                TextIcon(
                    symbol = UISymbols.SEARCH,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onValueChange("")
                            onClear?.invoke()
                        }
                    ) {
                        TextIcon(
                            symbol = UISymbols.CLEAR,
                            contentDescription = "Clear search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            enabled = enabled,
            textStyle = MaterialTheme.typography.bodyMedium
        )
        
        // Error message display
        if (isError && errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 11.sp
            )
        }
    }
}