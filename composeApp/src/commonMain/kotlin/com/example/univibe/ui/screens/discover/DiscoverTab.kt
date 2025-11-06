package com.example.univibe.ui.screens.discover

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols

/**
 * Discover Tab Screen for Voyager Navigation
 * 
 * Browse and discover communities, events, study groups
 */
object DiscoverTab : Tab {
    private fun readResolve(): Any = DiscoverTab

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 1u,
                title = "Discover"
            )
        }

    @Composable
    override fun Content() {
        DiscoverScreen()
    }
}