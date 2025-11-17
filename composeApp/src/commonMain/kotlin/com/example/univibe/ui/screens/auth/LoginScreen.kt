package com.example.univibe.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.design.UniVibeDesign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Professional login screen with comprehensive error handling
 * Provides secure access to existing accounts
 */
object LoginScreen : Screen {
    @Composable
    override fun Content() {
        LoginContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var rememberMe by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Sign In",
                        style = UniVibeDesign.Text.screenTitle()
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            "Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(UniVibeDesign.Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.lg)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            // Logo and welcome
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
            ) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "U",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Text(
                    text = "Welcome Back",
                    style = UniVibeDesign.Text.sectionTitle().copy(fontSize = 28.sp),
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "Sign in to continue to UniVibe",
                    style = UniVibeDesign.Text.bodySecondary(),
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(UniVibeDesign.Spacing.md))
            
            // Error message
            if (errorMessage != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(UniVibeDesign.Spacing.md),
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = errorMessage!!,
                            style = UniVibeDesign.Text.bodySecondary().copy(
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        )
                    }
                }
            }
            
            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { 
                    email = it
                    errorMessage = null
                },
                label = { Text("University Email") },
                placeholder = { Text("your.email@university.edu") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                isError = errorMessage != null,
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    errorMessage = null
                },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (showPassword) "Hide password" else "Show password",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = errorMessage != null,
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // Remember me & Forgot password
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it }
                    )
                    Text(
                        "Remember me",
                        style = UniVibeDesign.Text.bodySecondary()
                    )
                }
                
                TextButton(onClick = { 
                    // TODO: Navigate to forgot password screen
                }) {
                    Text(
                        "Forgot Password?",
                        style = UniVibeDesign.Text.buttonText().copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            
            // Sign In Button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Please fill in all fields"
                        return@Button
                    }
                    
                    if (!email.contains("@") || !email.endsWith(".edu")) {
                        errorMessage = "Please enter a valid university email"
                        return@Button
                    }
                    
                    isLoading = true
                    scope.launch {
                        // Mock authentication
                        delay(1500)
                        
                        // Simulate login validation
                        if (email == "test@university.edu" && password == "password") {
                            isLoading = false
                            // Navigate to main app
                            navigator.replaceAll(MainAppEntry)
                        } else {
                            isLoading = false
                            errorMessage = "Invalid email or password. Please check your credentials."
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
                colors = UniVibeDesign.Buttons.primaryColors(),
                shape = UniVibeDesign.Buttons.standardShape
            ) {
                if (isLoading) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Signing In...",
                            style = UniVibeDesign.Text.buttonText()
                        )
                    }
                } else {
                    Text(
                        text = "Sign In",
                        style = UniVibeDesign.Text.buttonText()
                    )
                }
            }
            
            // Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    "OR",
                    style = UniVibeDesign.Text.caption(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
            
            // Guest access
            OutlinedButton(
                onClick = {
                    // Navigate to main app as guest
                    navigator.replaceAll(MainAppEntry)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = UniVibeDesign.Buttons.outlinedColors(),
                shape = UniVibeDesign.Buttons.standardShape
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Browse as Guest",
                        style = UniVibeDesign.Text.buttonText()
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Sign Up Link
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UniVibeDesign.Spacing.md),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Don't have an account?",
                        style = UniVibeDesign.Text.bodySecondary()
                    )
                    TextButton(onClick = { navigator.push(UniversitySelectScreen) }) {
                        Text(
                            "Sign Up",
                            style = UniVibeDesign.Text.buttonText().copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}