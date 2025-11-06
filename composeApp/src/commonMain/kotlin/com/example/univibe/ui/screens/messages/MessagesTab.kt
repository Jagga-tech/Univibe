package com.example.univibe.ui.screens.messages

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols

/**
 * Messages Tab Screen for Voyager Navigation
 * 
 * Direct messaging and conversations with other users
 */
object MessagesTab : Tab {
    private fun readResolve(): Any = MessagesTab

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 3u,
                title = "Messages"
            )
        }

    @Composable
    override fun Content() {
        MessagesScreen()
    }
}