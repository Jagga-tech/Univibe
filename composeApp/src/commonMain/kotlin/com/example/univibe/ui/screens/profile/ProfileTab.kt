package com.example.univibe.ui.screens.profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person as PersonOutlined
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

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
            val isSelected = LocalTabNavigator.current.current == this
            val icon = if (isSelected) Icons.Filled.Person else PersonOutlined

            return TabOptions(
                index = 4u,
                title = "Profile",
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        ProfileScreen()
    }
}