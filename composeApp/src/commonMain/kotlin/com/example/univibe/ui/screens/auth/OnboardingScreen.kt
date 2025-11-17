package com.example.univibe.ui.screens.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.design.UniVibeDesign
import kotlinx.coroutines.launch

/**
 * Beautiful onboarding screen with horizontal pager
 * Introduces users to UniVibe's key features and benefits
 */
object OnboardingScreen : Screen {
    @Composable
    override fun Content() {
        OnboardingContent()
    }
}

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val backgroundColor: Color
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun OnboardingContent() {
    val navigator = LocalNavigator.currentOrThrow
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPageContent(onboardingPages[page])
            }
            
            // Bottom section
            Surface(
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(UniVibeDesign.Spacing.xl),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
                ) {
                    // Page indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(onboardingPages.size) { index ->
                            Box(
                                modifier = Modifier
                                    .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                                    .background(
                                        color = if (index == pagerState.currentPage) 
                                            MaterialTheme.colorScheme.primary 
                                        else 
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                        shape = CircleShape
                                    )
                            )
                            if (index < onboardingPages.size - 1) {
                                Spacer(modifier = Modifier.width(UniVibeDesign.Spacing.xs))
                            }
                        }
                    }
                    
                    // Buttons
                    if (pagerState.currentPage == onboardingPages.size - 1) {
                        // Last page - show Get Started
                        Button(
                            onClick = { navigator.replace(UniversitySelectScreen) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = UniVibeDesign.Buttons.primaryColors(),
                            shape = UniVibeDesign.Buttons.standardShape
                        ) {
                            Text(
                                "Get Started",
                                style = UniVibeDesign.Text.buttonText()
                            )
                        }
                        
                        TextButton(
                            onClick = { navigator.replace(LoginScreen) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "I Already Have an Account",
                                style = UniVibeDesign.Text.buttonText().copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    } else {
                        // Other pages - show Next + Skip
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = { navigator.replace(UniversitySelectScreen) }
                            ) {
                                Text(
                                    "Skip",
                                    style = UniVibeDesign.Text.buttonText().copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                            
                            Button(
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                },
                                colors = UniVibeDesign.Buttons.primaryColors(),
                                shape = UniVibeDesign.Buttons.standardShape
                            ) {
                                Text(
                                    "Next",
                                    style = UniVibeDesign.Text.buttonText()
                                )
                                Spacer(modifier = Modifier.width(UniVibeDesign.Spacing.xs))
                                Icon(
                                    Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(page.backgroundColor.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(UniVibeDesign.Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Surface(
                modifier = Modifier.size(120.dp),
                shape = MaterialTheme.shapes.extraLarge,
                color = page.backgroundColor.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = page.icon,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = page.backgroundColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(UniVibeDesign.Spacing.xxl))
            
            // Title
            Text(
                text = page.title,
                style = UniVibeDesign.Text.sectionTitle().copy(fontSize = 28.sp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(UniVibeDesign.Spacing.md))
            
            // Description
            Text(
                text = page.description,
                style = UniVibeDesign.Text.body().copy(fontSize = 18.sp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 28.sp
            )
        }
    }
}

// Onboarding pages data
private val onboardingPages = listOf(
    OnboardingPage(
        title = "Connect with Classmates",
        description = "Find students in your classes, join study groups, and build meaningful connections with your campus community.",
        icon = Icons.Default.People,
        backgroundColor = Color(0xFF6366F1) // Indigo
    ),
    OnboardingPage(
        title = "Study Together, Succeed Together",
        description = "Form study groups, share resources, and collaborate on assignments. Learning is better when you're not alone.",
        icon = Icons.Default.MenuBook,
        backgroundColor = Color(0xFF10B981) // Emerald
    ),
    OnboardingPage(
        title = "Discover Campus Life",
        description = "Never miss out on events, club meetings, job opportunities, and everything happening on your campus.",
        icon = Icons.Default.Event,
        backgroundColor = Color(0xFFF59E0B) // Amber
    ),
    OnboardingPage(
        title = "Find Your Opportunities",
        description = "Explore internships, part-time jobs, research positions, and career opportunities tailored to your campus.",
        icon = Icons.Default.Work,
        backgroundColor = Color(0xFFEF4444) // Red
    )
)