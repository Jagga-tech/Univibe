package com.example.univibe.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.design.UniVibeDesign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Professional registration screen with comprehensive validation
 * Guides users through account creation with their university email
 */
data class RegisterScreen(
    val university: University
) : Screen {
    @Composable
    override fun Content() {
        RegisterContent(university)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterContent(university: University) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    
    // Form state
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    // Validation errors
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var fullNameError by remember { mutableStateOf<String?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Create Account",
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
                .verticalScroll(scrollState)
                .padding(UniVibeDesign.Spacing.xl),
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.lg)
        ) {
            // University badge
            UniVibeDesign.StandardCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
                ) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = UniVibeDesign.Cards.standardShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.School,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = university.name,
                            style = UniVibeDesign.Text.cardTitle()
                        )
                        Text(
                            text = university.location,
                            style = UniVibeDesign.Text.bodySecondary()
                        )
                    }
                }
            }
            
            Text(
                text = "Join your campus community",
                style = UniVibeDesign.Text.sectionTitle()
            )
            
            // Full Name
            OutlinedTextField(
                value = fullName,
                onValueChange = { 
                    fullName = it
                    fullNameError = null
                },
                label = { Text("Full Name") },
                placeholder = { Text("Enter your full name") },
                modifier = Modifier.fillMaxWidth(),
                isError = fullNameError != null,
                supportingText = fullNameError?.let { { Text(it) } },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // University Email
            OutlinedTextField(
                value = email,
                onValueChange = { 
                    email = it
                    emailError = null
                },
                label = { Text("University Email") },
                placeholder = { Text("your.email@${getEmailDomain(university.name)}") },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError != null,
                supportingText = emailError?.let { { Text(it) } },
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = null
                },
                label = { Text("Password") },
                placeholder = { Text("Minimum 8 characters") },
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError != null,
                supportingText = passwordError?.let { { Text(it) } },
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
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // Confirm Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    confirmPasswordError = null
                },
                label = { Text("Confirm Password") },
                placeholder = { Text("Re-enter your password") },
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPasswordError != null,
                supportingText = confirmPasswordError?.let { { Text(it) } },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                        Icon(
                            if (showConfirmPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (showConfirmPassword) "Hide password" else "Show password",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // Password requirements
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(UniVibeDesign.Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
                ) {
                    Text(
                        text = "Password Requirements:",
                        style = UniVibeDesign.Text.caption().copy(fontWeight = FontWeight.SemiBold)
                    )
                    PasswordRequirement("At least 8 characters", password.length >= 8)
                    PasswordRequirement("Contains uppercase letter", password.any { it.isUpperCase() })
                    PasswordRequirement("Contains lowercase letter", password.any { it.isLowerCase() })
                    PasswordRequirement("Contains number", password.any { it.isDigit() })
                }
            }
            
            // Terms and Conditions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
            ) {
                Checkbox(
                    checked = agreedToTerms,
                    onCheckedChange = { agreedToTerms = it }
                )
                Text(
                    text = "I agree to the Terms of Service and Privacy Policy. I confirm that I am a current student at ${university.name}.",
                    style = UniVibeDesign.Text.bodySecondary()
                )
            }
            
            // Register Button
            Button(
                onClick = {
                    // Comprehensive validation
                    var isValid = true
                    
                    if (fullName.trim().length < 2) {
                        fullNameError = "Please enter your full name"
                        isValid = false
                    }
                    
                    if (!email.endsWith(".edu")) {
                        emailError = "Please use your university email (.edu domain)"
                        isValid = false
                    }
                    
                    if (password.length < 8) {
                        passwordError = "Password must be at least 8 characters"
                        isValid = false
                    } else if (!password.any { it.isUpperCase() } || 
                               !password.any { it.isLowerCase() } || 
                               !password.any { it.isDigit() }) {
                        passwordError = "Password must contain uppercase, lowercase, and number"
                        isValid = false
                    }
                    
                    if (password != confirmPassword) {
                        confirmPasswordError = "Passwords do not match"
                        isValid = false
                    }
                    
                    if (isValid && agreedToTerms) {
                        isLoading = true
                        scope.launch {
                            // Mock registration process
                            delay(2000)
                            isLoading = false
                            // Navigate to main app after successful registration
                            navigator.replaceAll(MainAppEntry)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = fullName.isNotBlank() && 
                         email.isNotBlank() && 
                         password.isNotBlank() && 
                         confirmPassword.isNotBlank() &&
                         agreedToTerms &&
                         !isLoading,
                colors = UniVibeDesign.Buttons.primaryColors(),
                shape = UniVibeDesign.Buttons.standardShape
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Create Account",
                        style = UniVibeDesign.Text.buttonText()
                    )
                }
            }
            
            // Already have account
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    style = UniVibeDesign.Text.bodySecondary()
                )
                TextButton(onClick = { navigator.push(LoginScreen) }) {
                    Text(
                        "Sign In",
                        style = UniVibeDesign.Text.buttonText().copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(UniVibeDesign.Spacing.lg))
        }
    }
}

/**
 * Password requirement indicator
 */
@Composable
private fun PasswordRequirement(
    requirement: String,
    isMet: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            if (isMet) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = if (isMet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = requirement,
            style = UniVibeDesign.Text.caption().copy(
                color = if (isMet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

/**
 * Helper function to get university email domain
 */
private fun getEmailDomain(universityName: String): String {
    return when {
        universityName.contains("Stanford") -> "stanford.edu"
        universityName.contains("UC Berkeley") -> "berkeley.edu"
        universityName.contains("MIT") -> "mit.edu"
        universityName.contains("Harvard") -> "harvard.edu"
        universityName.contains("UCLA") -> "ucla.edu"
        universityName.contains("USC") -> "usc.edu"
        universityName.contains("NYU") -> "nyu.edu"
        universityName.contains("Columbia") -> "columbia.edu"
        else -> "university.edu"
    }
}