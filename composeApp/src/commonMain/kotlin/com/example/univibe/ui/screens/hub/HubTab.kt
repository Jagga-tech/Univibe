package com.example.univibe.ui.screens.hub

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols

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
            val icon = if (isSelected) UISymbols.DASHBOARD_FILLED else UISymbols.DASHBOARD_OUTLINED

            return TabOptions(
                index = 2u,
                title = "Hub",
                icon = {
                    TextIcon(symbol = icon)
                }
            )
        }

    @Composable
    override fun Content() {
        HubScreen()
    }
}