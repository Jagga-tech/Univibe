package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.domain.models.User
import com.example.univibe.ui.components.UserAvatar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Validation result data class
data class ValidationResult(
    val isValid: Boolean = true,
    val errorMessage: String? = null
)

data class EditProfileScreenData(
    val user: User? = null
) : Screen {
    @Composable
    override fun Content() {
        if (user != null) {
            EditProfileContent(user)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileContent(currentUser: User) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    
    // Form state
    var name by remember { mutableStateOf(currentUser.fullName) }
    var username by remember { mutableStateOf(currentUser.username) }
    var bio by remember { mutableStateOf(currentUser.bio ?: "") }
    var major by remember { mutableStateOf(currentUser.major ?: "") }
    var year by remember { mutableStateOf(currentUser.graduationYear?.toString() ?: "") }
    var email by remember { mutableStateOf(currentUser.email) }
    var phone by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    
    // Validation state
    var nameError by remember { mutableStateOf<String?>(null) }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var websiteError by remember { mutableStateOf<String?>(null) }
    
    var isSaving by remember { mutableStateOf(false) }
    var showUnsavedChangesDialog by remember { mutableStateOf(false) }
    
    val hasChanges = remember(name, username, bio, major, year, email, phone, website, location) {
        name != currentUser.fullName ||
        username != currentUser.username ||
        bio != (currentUser.bio ?: "") ||
        major != (currentUser.major ?: "") ||
        year != (currentUser.graduationYear?.toString() ?: "") ||
        email != currentUser.email ||
        phone.isNotEmpty() ||
        website.isNotEmpty() ||
        location.isNotEmpty()
    }
    
    // Validation functions
    fun validateName(): ValidationResult {
        return if (name.isBlank()) {
            ValidationResult(false, "Name is required")
        } else if (name.length < 2) {
            ValidationResult(false, "Name must be at least 2 characters")
        } else {
            ValidationResult(true)
        }
    }
    
    fun validateUsername(): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult(false, "Username is required")
            username.length < 3 -> ValidationResult(false, "Username must be at least 3 characters")
            !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> ValidationResult(false, "Only letters, numbers, and underscores allowed")
            else -> ValidationResult(true)
        }
    }
    
    fun validateEmail(): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email is required")
            !email.contains("@") || !email.contains(".") -> ValidationResult(false, "Invalid email format")
            else -> ValidationResult(true)
        }
    }
    
    fun validatePhone(): ValidationResult {
        return if (phone.isNotEmpty() && phone.length < 10) {
            ValidationResult(false, "Phone must be at least 10 characters")
        } else {
            ValidationResult(true)
        }
    }
    
    fun validateWebsite(): ValidationResult {
        return if (website.isNotEmpty() && !website.startsWith("http")) {
            ValidationResult(false, "Website must start with http:// or https://")
        } else {
            ValidationResult(true)
        }
    }
    
    fun validateAll(): Boolean {
        var isValid = true
        
        val nameValidation = validateName()
        nameError = nameValidation.errorMessage
        if (!nameValidation.isValid) isValid = false
        
        val usernameValidation = validateUsername()
        usernameError = usernameValidation.errorMessage
        if (!usernameValidation.isValid) isValid = false
        
        val emailValidation = validateEmail()
        emailError = emailValidation.errorMessage
        if (!emailValidation.isValid) isValid = false
        
        val phoneValidation = validatePhone()
        phoneError = phoneValidation.errorMessage
        if (!phoneValidation.isValid) isValid = false
        
        val websiteValidation = validateWebsite()
        websiteError = websiteValidation.errorMessage
        if (!websiteValidation.isValid) isValid = false
        
        return isValid
    }
    
    // Unsaved changes dialog
    if (showUnsavedChangesDialog) {
        AlertDialog(
            onDismissRequest = { showUnsavedChangesDialog = false },
            title = { Text("Discard changes?") },
            text = { Text("You have unsaved changes. Are you sure you want to discard them?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showUnsavedChangesDialog = false
                        navigator.pop()
                    }
                ) {
                    Text("Discard")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showUnsavedChangesDialog = false }
                ) {
                    Text("Keep Editing")
                }
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (hasChanges) {
                            showUnsavedChangesDialog = true
                        } else {
                            navigator.pop()
                        }
                    }) {
                        Icon(Icons.Default.Close, "Cancel")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (validateAll()) {
                                isSaving = true
                                scope.launch {
                                    delay(1500)
                                    isSaving = false
                                    // TODO: Save to backend
                                    navigator.pop()
                                }
                            }
                        },
                        enabled = hasChanges && !isSaving
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Save")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Profile Picture Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    UserAvatar(
                        imageUrl = currentUser.avatarUrl,
                        name = currentUser.fullName,
                        size = 100.dp
                    )
                    
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 4.dp, y = 4.dp)
                            .clickable { /* TODO: Open image picker */ },
                        shape = androidx.compose.foundation.shape.CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        shadowElevation = 2.dp
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Change photo",
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                TextButton(onClick = { /* Change photo */ }) {
                    Text("Change Profile Photo")
                }
            }
            
            Divider()
            
            // Form Fields
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Basic Info Section
                Text(
                    text = "Basic Information",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Name Field
                OutlinedTextField(
                    value = name,
                    onValueChange = { 
                        name = it
                        nameError = null
                    },
                    label = { Text("Full Name") },
                    placeholder = { Text("Your full name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nameError != null,
                    supportingText = nameError?.let { { Text(it) } },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    }
                )
                
                // Username Field
                OutlinedTextField(
                    value = username,
                    onValueChange = { 
                        username = it.lowercase().replace(" ", "")
                        usernameError = null
                    },
                    label = { Text("Username") },
                    placeholder = { Text("username") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = usernameError != null,
                    supportingText = usernameError?.let { { Text(it) } },
                    singleLine = true,
                    prefix = { Text("@") },
                    leadingIcon = {
                        Icon(Icons.Default.AlternateEmail, contentDescription = null)
                    }
                )
                
                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        emailError = null
                    },
                    label = { Text("Email") },
                    placeholder = { Text("your@email.com") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError != null,
                    supportingText = emailError?.let { { Text(it) } },
                    singleLine = true,
                    keyboardType = KeyboardType.Email,
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    }
                )
                
                // Bio Field
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    placeholder = { Text("Tell us about yourself (max 150 chars)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    supportingText = { Text("${bio.length}/150") },
                    leadingIcon = {
                        Icon(Icons.Default.Description, contentDescription = null)
                    }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Academic Info Section
                Text(
                    text = "Academic Information",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Major Field
                OutlinedTextField(
                    value = major,
                    onValueChange = { major = it },
                    label = { Text("Major") },
                    placeholder = { Text("e.g., Computer Science") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.School, contentDescription = null)
                    }
                )
                
                // Year Dropdown
                var yearExpanded by remember { mutableStateOf(false) }
                val years = listOf("Freshman", "Sophomore", "Junior", "Senior", "Graduate", "Alumni")
                
                ExposedDropdownMenuBox(
                    expanded = yearExpanded,
                    onExpandedChange = { yearExpanded = it }
                ) {
                    OutlinedTextField(
                        value = year,
                        onValueChange = {},
                        label = { Text("Year") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpanded) },
                        leadingIcon = {
                            Icon(Icons.Default.CalendarToday, contentDescription = null)
                        }
                    )
                    
                    ExposedDropdownMenu(
                        expanded = yearExpanded,
                        onDismissRequest = { yearExpanded = false }
                    ) {
                        years.forEach { yearOption ->
                            DropdownMenuItem(
                                text = { Text(yearOption) },
                                onClick = {
                                    year = yearOption
                                    yearExpanded = false
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Contact & Social Section
                Text(
                    text = "Contact & Social",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Phone Field
                OutlinedTextField(
                    value = phone,
                    onValueChange = { 
                        phone = it
                        phoneError = null
                    },
                    label = { Text("Phone") },
                    placeholder = { Text("+1 (555) 000-0000") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = phoneError != null,
                    supportingText = phoneError?.let { { Text(it) } },
                    singleLine = true,
                    keyboardType = KeyboardType.Phone,
                    leadingIcon = {
                        Icon(Icons.Default.Phone, contentDescription = null)
                    }
                )
                
                // Website Field
                OutlinedTextField(
                    value = website,
                    onValueChange = { 
                        website = it
                        websiteError = null
                    },
                    label = { Text("Website") },
                    placeholder = { Text("https://example.com") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = websiteError != null,
                    supportingText = websiteError?.let { { Text(it) } },
                    singleLine = true,
                    keyboardType = KeyboardType.Uri,
                    leadingIcon = {
                        Icon(Icons.Default.Language, contentDescription = null)
                    }
                )
                
                // Location Field
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    placeholder = { Text("City, State") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                    }
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
