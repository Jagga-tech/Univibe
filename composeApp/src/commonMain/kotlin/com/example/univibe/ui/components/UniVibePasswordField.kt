package com.example.univibe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.theme.Dimensions

/**
 * Password input field with toggle visibility button.
 *
 * Features:
 * - Password masking/unmasking toggle
 * - Error state with message display
 * - Leading icon support
 * - Full accessibility support
 *
 * @param value Current password value
 * @param onValueChange Callback when value changes
 * @param modifier Modifier for customization
 * @param label Optional label text
 * @param placeholder Optional placeholder text
 * @param isError Whether field is in error state
 * @param errorMessage Error message to display
 * @param enabled Whether field is enabled
 */
@Composable
fun UniVibePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    enabled: Boolean = true
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    
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
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) {
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (isPasswordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        },
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
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