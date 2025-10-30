package com.example.univibe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.theme.Dimensions

/**
 * Material 3 OutlinedTextField wrapper for UniVibe with consistent styling.
 */
@Composable
fun UniVibeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    enabled: Boolean = true
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.InputHeight),
            label = if (label.isNotEmpty()) {
                { Text(label) }
            } else {
                null
            },
            placeholder = if (placeholder.isNotEmpty()) {
                { Text(placeholder) }
            } else {
                null
            },
            leadingIcon = if (leadingIcon != null) {
                {
                    androidx.compose.material3.Icon(
                        imageVector = leadingIcon,
                        contentDescription = "Leading icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                null
            },
            trailingIcon = if (trailingIcon != null) {
                {
                    androidx.compose.material3.Icon(
                        imageVector = trailingIcon,
                        contentDescription = "Trailing icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                null
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            maxLines = maxLines,
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