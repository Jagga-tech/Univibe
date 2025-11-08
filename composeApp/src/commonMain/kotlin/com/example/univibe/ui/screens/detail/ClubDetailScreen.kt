package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.*
import com.example.univibe.ui.screens.home.PostCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.util.ShareHelper

data class ClubDetailScreen(val clubId: String) : Screen {
    @Composable
    override fun Content() {
        ClubDetailScreenContent(clubId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClubDetailScreenContent(clubId: String) {
    val navigator = LocalNavigator.currentOrThrow
    val club = remember { MockClubs.getClubById(clubId) }
    var isMember by remember { mutableStateOf(club?.isMember ?: false) }
    var memberCount by remember { mutableStateOf(club?.memberCount ?: 0) }
    var selectedTab by remember { mutableStateOf(0) }
    
    if (club == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Club Not Found") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            TextIcon(
                                symbol = UISymbols.BACK,
                                contentDescription = "Back",
                                fontSize = 20
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Club not found")
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(club.name) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.BACK,
                            contentDescription = "Back",
                            fontSize = 20
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        val shareText = ShareHelper.shareClub(club)
                        println("Share: $shareText")
                    }) {
                        TextIcon(
                            symbol = UISymbols.SHARE,
                            contentDescription = "Share",
                            fontSize = 20
                        )
                    }
                    IconButton(onClick = { /* TODO: More options */ }) {
                        TextIcon(
                            symbol = UISymbols.MORE_VERT,
                            contentDescription = "More",
                            fontSize = 20
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 2.dp
            ) {
                Button(
                    text = if (isMember) "Leave Club" else "Join Club",
                    onClick = {
                        if (isMember) {
                            MockClubs.leaveClub(clubId)
                            memberCount--
                        } else {
                            MockClubs.joinClub(clubId)
                            memberCount++
                        }
                        isMember = !isMember
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Club Header
            ClubHeader(
                club = club,
                memberCount = memberCount,
                isMember = isMember
            )
            
            // Tabs
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("About") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Events (${club.upcomingEvents.size})") }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("Members") }
                )
                Tab(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    text = { Text("Posts") }
                )
            }
            
            // Tab Content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        // About Tab
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Text(
                                    "About",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    club.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                
                                if (club.meetingSchedule != null) {
                                    ClubInfoRow(
                                        icon = PlatformIcons.Schedule,
                                        label = "Meeting Schedule",
                                        value = club.meetingSchedule
                                    )
                                }
                                
                                if (club.meetingLocation != null) {
                                    ClubInfoRow(
                                        icon = PlatformIcons.Place,
                                        label = "Meeting Location",
                                        value = club.meetingLocation
                                    )
                                }
                                
                                // President Info
                                Text(
                                    "Leadership",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable {
                                        navigator.push(UserProfileScreen(club.presidentId))
                                    }
                                ) {
                                    UserAvatar(
                                        imageUrl = club.president.avatarUrl,
                                        name = club.president.fullName,
                                        size = Dimensions.AvatarSize.medium
                                    )
                                    Column {
                                        Text(
                                            club.president.fullName,
                                            style = MaterialTheme.typography.titleSmall
                                        )
                                        Text(
                                            "President",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                
                                // Social Media
                                if (club.socialMedia != null) {
                                    Text(
                                        "Connect With Us",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        club.socialMedia.instagram?.let {
                                            SocialMediaLink(
                                                icon = PlatformIcons.PhotoCamera,
                                                label = it
                                            )
                                        }
                                        club.socialMedia.website?.let {
                                            SocialMediaLink(
                                                icon = PlatformIcons.Language,
                                                label = it
                                            )
                                        }
                                        club.socialMedia.email?.let {
                                            SocialMediaLink(
                                                icon = PlatformIcons.Email,
                                                label = it
                                            )
                                        }
                                    }
                                }
                                
                                // Tags
                                if (club.tags.isNotEmpty()) {
                                    Text(
                                        "Tags",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        club.tags.forEach { tag ->
                                            SuggestionChip(
                                                onClick = { /* TODO */ },
                                                label = { Text("#$tag") }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        // Events Tab
                        if (club.upcomingEvents.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            PlatformIcons.Event,
                                            contentDescription = null,
                                            modifier = Modifier.size(48.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            "No upcoming events",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "Check back later for club events",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        } else {
                            items(club.upcomingEvents) { event ->
                                Card(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            event.title,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            event.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                    2 -> {
                        // Members Tab
                        item {
                            Text(
                                "$memberCount members",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        items(club.members) { member ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navigator.push(UserProfileScreen(member.id))
                                    }
                                    .padding(vertical = Dimensions.Spacing.sm),
                                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                UserAvatar(
                                    imageUrl = member.avatarUrl,
                                    name = member.fullName,
                                    size = Dimensions.AvatarSize.medium
                                )
                                Column {
                                    Text(
                                        member.fullName,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        member.major ?: "Student",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                    3 -> {
                        // Posts Tab
                        if (club.recentPosts.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Icon(
                                            PlatformIcons.Article,
                                            contentDescription = null,
                                            modifier = Modifier.size(48.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            "No posts yet",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            "Club posts will appear here",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        } else {
                            items(club.recentPosts) { post ->
                                PostCard(
                                    post = post,
                                    onLikeClick = { /* TODO */ },
                                    onCommentClick = { navigator.push(PostDetailScreen(post.id)) },
                                    onShareClick = { /* TODO */ },
                                    onPostClick = { navigator.push(PostDetailScreen(post.id)) },
                                    onUserClick = { navigator.push(UserProfileScreen(post.authorId)) }
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
private fun ClubHeader(
    club: Club,
    memberCount: Int,
    isMember: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Logo
        Surface(
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    club.category.emoji,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
        
        // Name and verified badge
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = club.name,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            if (club.isVerified) {
                Icon(
                    imageVector = PlatformIcons.Verified,
                    contentDescription = "Verified",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        // Category
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = club.category.displayName,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        
        // Member count
        Text(
            "$memberCount ${if (memberCount == 1) "member" else "members"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ClubInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SocialMediaLink(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { /* TODO: Open link */ }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier.height(48.dp)
    ) {
        Text(text)
    }
}