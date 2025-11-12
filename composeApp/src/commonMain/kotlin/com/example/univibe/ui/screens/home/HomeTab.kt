package com.example.univibe.ui.screens.home

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.screens.create.CreatePostScreen
import com.example.univibe.ui.utils.UISymbols

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
            return TabOptions(
                index = 0u,
                title = "Home"
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
                    TextIcon(symbol = UISymbols.ADD, contentDescription = "Create Post")
                }
            }
        ) { paddingValues ->
            HomeScreen()
        }
    }
}