package com.example.univibe.ui.screens.hub

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.outlined.Dashboard as DashboardOutlined
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

/**
 * Hub Tab Screen for Voyager Navigation
 * 
 * Central hub for events, clubs, study sessions, and campus resources
 */
object HubTab : Tab {
    private fun readResolve(): Any = HubTab

    override val options: TabOptions
        @Composable
        get() {
            val isSelected = LocalTabNavigator.current.current == this
            val icon = if (isSelected) Icons.Filled.Dashboard else DashboardOutlined

            return TabOptions(
                index = 2u,
                title = "Hub",
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        HubScreen()
    }
}