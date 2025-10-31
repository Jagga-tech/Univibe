package com.example.univibe.ui.screens.messages

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

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
            val isSelected = LocalTabNavigator.current.current == this
            val icon = if (isSelected) Icons.Filled.ChatBubble else Icons.Outlined.ChatBubbleOutline

            return TabOptions(
                index = 3u,
                title = "Messages",
                icon = icon
            )
        }

    @Composable
    override fun Content() {
        MessagesScreen()
    }
}