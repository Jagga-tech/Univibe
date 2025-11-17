package com.example.univibe.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.design.UniVibeDesign

/**
 * Beautiful welcome screen showcasing UniVibe's value proposition
 * Designed to convince students to join the platform
 */
object WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        WelcomeContent()
    }
}

@Composable
private fun WelcomeContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        Color.White
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(UniVibeDesign.Spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Hero Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
            ) {
                // Logo
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "U",
                            fontSize = 56.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Text(
                    text = "Welcome to UniVibe",
                    style = UniVibeDesign.Text.screenTitle().copy(fontSize = 32.sp),
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "Find Your Study Partners.\nSucceed Together.",
                    style = UniVibeDesign.Text.bodySecondary().copy(fontSize = 18.sp),
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp
                )
            }
            
            // Features
            Column(
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.lg)
            ) {
                FeatureRow(
                    icon = Icons.Default.People,
                    title = "Connect with Classmates",
                    description = "Build your campus network and find study partners"
                )
                
                FeatureRow(
                    icon = Icons.Default.MenuBook,
                    title = "Study Groups & Sessions",
                    description = "Collaborate on assignments and prepare for exams together"
                )
                
                FeatureRow(
                    icon = Icons.Default.Event,
                    title = "Campus Events & Activities",
                    description = "Discover clubs, events, and opportunities on campus"
                )
            }
            
            // CTA Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
            ) {
                Button(
                    onClick = { navigator.push(UniversitySelectScreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = UniVibeDesign.Buttons.primaryColors(),
                    shape = UniVibeDesign.Buttons.standardShape
                ) {
                    Text(
                        text = "Get Started",
                        style = UniVibeDesign.Text.buttonText().copy(fontSize = 18.sp)
                    )
                }
                
                OutlinedButton(
                    onClick = { navigator.push(LoginScreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = UniVibeDesign.Buttons.outlinedColors(),
                    shape = UniVibeDesign.Buttons.standardShape
                ) {
                    Text(
                        text = "I Already Have an Account",
                        style = UniVibeDesign.Text.buttonText().copy(fontSize = 16.sp)
                    )
                }
            }
        }
    }
}

/**
 * Feature row component showcasing app capabilities
 */
@Composable
private fun FeatureRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(56.dp),
            shape = UniVibeDesign.Cards.standardShape,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = UniVibeDesign.Text.cardTitle()
            )
            Text(
                text = description,
                style = UniVibeDesign.Text.bodySecondary()
            )
        }
    }
}