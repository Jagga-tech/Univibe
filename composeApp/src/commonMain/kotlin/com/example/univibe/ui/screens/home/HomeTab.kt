package com.example.univibe.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Home as HomeOutlined
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.screens.create.CreatePostScreen

/**
 * Home Tab Screen for Voyager Navigation
 * 
 * Main feed displaying stories, quick access, and posts
 */
object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

    override val options: TabOptions
        @Composable
        get() {
            val isSelected = LocalTabNavigator.current.current == this
            val icon = if (isSelected) Icons.Filled.Home else HomeOutlined

            return TabOptions(
                index = 0u,
                title = "Home",
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navigator.push(CreatePostScreen) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Create Post")
                }
            }
        ) { paddingValues ->
            HomeScreen()
        }
    }
}