package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.background
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
import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.UserProfileData
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Edit profile screen for users to update their profile information.
 */
object EditProfileScreen : Screen {
    @Composable
    override fun Content() {
        EditProfileScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    var profile by remember { mutableStateOf(getCurrentUserProfile()) }
    var fullName by remember { mutableStateOf(profile.fullName) }
    var bio by remember { mutableStateOf(profile.bio) }
    var major by remember { mutableStateOf(profile.major) }
    var graduationYear by remember { mutableStateOf(profile.graduationYear?.toString() ?: "") }
    var website by remember { mutableStateOf(profile.website ?: "") }
    var location by remember { mutableStateOf(profile.location ?: "") }
    var pronouns by remember { mutableStateOf(profile.pronouns ?: "") }
    var isSaving by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(UISymbols.BACK, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(Dimensions.Spacing.md)
        ) {
            // Profile Picture Section
            item {
                ProfileEditSection("Profile Picture") {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer,
                                shape = androidx.compose.foundation.shape.CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📸",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                    Spacer(modifier = Modifier.width(Dimensions.Spacing.md))
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Upload Photo")
                        }
                        OutlinedButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Remove Photo")
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Basic Information Section
            item {
                ProfileEditSection("Basic Information") {
                    EditTextField(
                        label = "Full Name",
                        value = fullName,
                        onValueChange = { fullName = it }
                    )
                }
            }

            item {
                EditTextField(
                    label = "Bio",
                    value = bio,
                    onValueChange = { bio = it },
                    maxLines = 4
                )
            }

            item {
                EditTextField(
                    label = "Website",
                    value = website,
                    onValueChange = { website = it }
                )
            }

            item {
                EditTextField(
                    label = "Location",
                    value = location,
                    onValueChange = { location = it }
                )
            }

            item {
                EditTextField(
                    label = "Pronouns",
                    value = pronouns,
                    onValueChange = { pronouns = it }
                )
            }

            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }

            // Academic Information Section
            item {
                ProfileEditSection("Academic Information") {
                    EditTextField(
                        label = "Major",
                        value = major,
                        onValueChange = { major = it }
                    )
                }
            }

            item {
                EditTextField(
                    label = "Graduation Year",
                    value = graduationYear,
                    onValueChange = { 
                        if (it.isEmpty() || it.all { c -> c.isDigit() }) {
                            graduationYear = it
                        }
                    }
                )
            }

            // Action Buttons
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
                PrimaryButton(
                    text = if (isSaving) "Saving..." else "Save Changes",
                    onClick = {
                        isSaving = true
                        val updatedProfile = profile.copy(
                            fullName = fullName,
                            bio = bio,
                            major = major,
                            graduationYear = graduationYear.toIntOrNull(),
                            website = website,
                            location = location,
                            pronouns = pronouns
                        )
                        updateUserProfile(updatedProfile)
                        isSaving = false
                        navigator.pop()
                    },
                    enabled = !isSaving,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
                OutlinedButton(
                    onClick = { navigator.pop() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Section header for edit profile form.
 */
@Composable
private fun ProfileEditSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        content()
    }
}

/**
 * Text field for editing profile information.
 */
@Composable
private fun EditTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.Spacing.sm),
        maxLines = maxLines,
        singleLine = maxLines == 1
    )
}