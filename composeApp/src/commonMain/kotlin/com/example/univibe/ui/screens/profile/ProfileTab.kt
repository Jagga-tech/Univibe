package com.example.univibe.ui.screens.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols

/**
 * Profile Tab Screen for Voyager Navigation
 * 
 * User profile, settings, and account management
 */
object ProfileTab : Tab {
    private fun readResolve(): Any = ProfileTab

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 4u,
                title = "Profile"
            )
        }

    @Composable
    override fun Content() {
        ProfileScreen()
    }
}