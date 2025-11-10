package com.example.univibe.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.OutlineButton
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.ui.utils.UISymbols

/**
 * Data class representing a post search result.
 */
data class PostSearchResult(
    val id: String,
    val title: String,
    val preview: String,
    val authorName: String,
    val authorAvatarUrl: String? = null,
    val timestamp: String,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isLiked: Boolean = false
)

/**
 * Data class representing a user search result.
 */
data class UserSearchResult(
    val id: String,
    val name: String,
    val handle: String,
    val bio: String,
    val avatarUrl: String? = null,
    val followersCount: Int = 0,
    val isFollowing: Boolean = false
)

/**
 * Data class representing a group search result.
 */
data class GroupSearchResult(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val memberCount: Int = 0,
    val isMember: Boolean = false
)

/**
 * Data class representing an event search result.
 */
data class EventSearchResult(
    val id: String,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val attendeeCount: Int = 0,
    val isAttending: Boolean = false
)

/**
 * Search Results screen - displays search results across multiple categories
 * with tab-based filtering and sort options.
 *
 * @param searchQuery Initial search query text
 * @param postResults List of post search results
 * @param userResults List of user search results
 * @param groupResults List of group search results
 * @param eventResults List of event search results
 * @param onBackClick Callback when back button is clicked
 * @param onSearchQueryChange Callback when search query changes
 * @param onPostClick Callback when a post result is clicked
 * @param onPostLikeClick Callback when post like button is clicked
 * @param onUserClick Callback when a user result is clicked
 * @param onUserFollowClick Callback when user follow button is clicked
 * @param onGroupClick Callback when a group result is clicked
 * @param onGroupJoinClick Callback when group join button is clicked
 * @param onEventClick Callback when an event result is clicked
 * @param onEventRsvpClick Callback when event RSVP button is clicked
 */
@Composable
fun SearchResultsScreen(
    searchQuery: String = "",
    postResults: List<PostSearchResult> = getSamplePostResults(),
    userResults: List<UserSearchResult> = getSampleUserResults(),
    groupResults: List<GroupSearchResult> = getSampleGroupResults(),
    eventResults: List<EventSearchResult> = getSampleEventResults(),
    onBackClick: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    onPostClick: (String) -> Unit = {},
    onPostLikeClick: (String) -> Unit = {},
    onUserClick: (String) -> Unit = {},
    onUserFollowClick: (String) -> Unit = {},
    onGroupClick: (String) -> Unit = {},
    onGroupJoinClick: (String) -> Unit = {},
    onEventClick: (String) -> Unit = {},
    onEventRsvpClick: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf(searchQuery) }
    var selectedTab by remember { mutableStateOf(0) }
    var selectedFilter by remember { mutableStateOf(0) }
    val tabTitles = listOf("Posts", "Users", "Groups", "Events")
    val filterOptions = listOf("Trending", "Recent", "Most Popular")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Search header with back button and search field
        SearchHeader(
            searchQuery = query,
            onQueryChange = { newQuery ->
                query = newQuery
                onSearchQueryChange(newQuery)
            },
            onBackClick = onBackClick
        )

        // Filter chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.Spacing.md, vertical = Dimensions.Spacing.sm),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            items(filterOptions.size) { index ->
                FilterChip(
                    label = filterOptions[index],
                    isSelected = selectedFilter == index,
                    onClick = { selectedFilter = index }
                )
            }
        }

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )
            }
        }

        // Tab content
        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> PostResultsTab(
                    results = postResults,
                    onPostClick = onPostClick,
                    onPostLikeClick = onPostLikeClick
                )
                1 -> UserResultsTab(
                    results = userResults,
                    onUserClick = onUserClick,
                    onUserFollowClick = onUserFollowClick
                )
                2 -> GroupResultsTab(
                    results = groupResults,
                    onGroupClick = onGroupClick,
                    onGroupJoinClick = onGroupJoinClick
                )
                3 -> EventResultsTab(
                    results = eventResults,
                    onEventClick = onEventClick,
                    onEventRsvpClick = onEventRsvpClick
                )
            }
        }
    }
}

/**
 * Search header with back button and search field.
 */
@Composable
private fun SearchHeader(
    searchQuery: String = "",
    onQueryChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(40.dp)
        ) {
            TextIcon(
                symbol = UISymbols.BACK,
                contentDescription = "Back",
                fontSize = 24,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Search field
        TextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            placeholder = {
                Text(
                    text = "Search...",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            leadingIcon = {
                TextIcon(
                    symbol = UISymbols.SEARCH,
                    contentDescription = "Search",
                    fontSize = 20,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { onQueryChange("") },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = PlatformIcons.Close,
                            contentDescription = "Clear",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(Dimensions.CornerRadius.md),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant
            ),
            textStyle = MaterialTheme.typography.bodySmall
        )
    }
}

/**
 * Filter chip for sorting/filtering search results.
 */
@Composable
private fun FilterChip(
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(Dimensions.CornerRadius.lg)
            )
            .padding(horizontal = Dimensions.Spacing.md, vertical = Dimensions.Spacing.sm),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Tab content showing post search results.
 */
@Composable
private fun PostResultsTab(
    results: List<PostSearchResult> = emptyList(),
    onPostClick: (String) -> Unit = {},
    onPostLikeClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        if (results.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No posts found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(results.size) { index ->
                PostSearchResultCard(
                    result = results[index],
                    onClick = { onPostClick(results[index].id) },
                    onLikeClick = { onPostLikeClick(results[index].id) }
                )
            }
        }

        // Bottom padding for navigation
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Individual post search result card.
 */
@Composable
private fun PostSearchResultCard(
    result: PostSearchResult,
    onClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            // Author info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    imageUrl = result.authorAvatarUrl,
                    size = Dimensions.AvatarSize.small
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = result.authorName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = result.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Post title
            Text(
                text = result.title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Post preview
            Text(
                text = result.preview,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Engagement stats and like button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${result.likeCount} likes · ${result.commentCount} comments",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                IconButton(
                    onClick = onLikeClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    TextIcon(
                        symbol = if (result.isLiked) UISymbols.FAVORITE else UISymbols.FAVORITE_BORDER,
                        contentDescription = "Like",
                        fontSize = 20,
                        tint = if (result.isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Tab content showing user search results.
 */
@Composable
private fun UserResultsTab(
    results: List<UserSearchResult> = emptyList(),
    onUserClick: (String) -> Unit = {},
    onUserFollowClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        if (results.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No users found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(results.size) { index ->
                UserSearchResultCard(
                    result = results[index],
                    onClick = { onUserClick(results[index].id) },
                    onFollowClick = { onUserFollowClick(results[index].id) }
                )
            }
        }

        // Bottom padding for navigation
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Individual user search result card.
 */
@Composable
private fun UserSearchResultCard(
    result: UserSearchResult,
    onClick: () -> Unit = {},
    onFollowClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            UserAvatar(
                imageUrl = result.avatarUrl,
                size = Dimensions.AvatarSize.medium
            )

            // User info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                Text(
                    text = result.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "@${result.handle}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = result.bio,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "${result.followersCount} followers",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Follow button
            OutlineButton(
                text = if (result.isFollowing) "Following" else "Follow",
                onClick = onFollowClick,
                modifier = Modifier
                    .width(90.dp)
                    .height(36.dp)
            )
        }
    }
}

/**
 * Tab content showing group search results.
 */
@Composable
private fun GroupResultsTab(
    results: List<GroupSearchResult> = emptyList(),
    onGroupClick: (String) -> Unit = {},
    onGroupJoinClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        if (results.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No groups found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(results.size) { index ->
                GroupSearchResultCard(
                    result = results[index],
                    onClick = { onGroupClick(results[index].id) },
                    onJoinClick = { onGroupJoinClick(results[index].id) }
                )
            }
        }

        // Bottom padding for navigation
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Individual group search result card.
 */
@Composable
private fun GroupSearchResultCard(
    result: GroupSearchResult,
    onClick: () -> Unit = {},
    onJoinClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            // Group header with name and category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = result.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(Dimensions.CornerRadius.small)
                            )
                            .padding(horizontal = Dimensions.Spacing.sm, vertical = 2.dp)
                    ) {
                        Text(
                            text = result.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            // Description
            Text(
                text = result.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Footer with member count and join button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${result.memberCount} members",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                OutlineButton(
                    text = if (result.isMember) "Joined" else "+ Join",
                    onClick = onJoinClick,
                    modifier = Modifier
                        .width(80.dp)
                        .height(32.dp)
                )
            }
        }
    }
}

/**
 * Tab content showing event search results.
 */
@Composable
private fun EventResultsTab(
    results: List<EventSearchResult> = emptyList(),
    onEventClick: (String) -> Unit = {},
    onEventRsvpClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        if (results.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No events found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(results.size) { index ->
                EventSearchResultCard(
                    result = results[index],
                    onClick = { onEventClick(results[index].id) },
                    onRsvpClick = { onEventRsvpClick(results[index].id) }
                )
            }
        }

        // Bottom padding for navigation
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Individual event search result card.
 */
@Composable
private fun EventSearchResultCard(
    result: EventSearchResult,
    onClick: () -> Unit = {},
    onRsvpClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            // Event title
            Text(
                text = result.title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Event details (date, time, location)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                DetailRow(label = "Date", value = result.date)
                DetailRow(label = "Time", value = result.time)
                DetailRow(label = "Location", value = result.location)
            }

            // Footer with attendee count and RSVP button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${result.attendeeCount} attending",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                PrimaryButton(
                    text = if (result.isAttending) "✓ Going" else "RSVP",
                    onClick = onRsvpClick,
                    modifier = Modifier
                        .width(90.dp)
                        .height(32.dp)
                )
            }
        }
    }
}

/**
 * Helper composable for event detail rows.
 */
@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(50.dp)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Sample data generators for preview and testing.
 */

private fun getSamplePostResults() = listOf(
    PostSearchResult(
        id = "post1",
        title = "Best study spots on campus",
        preview = "Found 5 amazing quiet places perfect for focused study sessions...",
        authorName = "Alex Johnson",
        timestamp = "2 hours ago",
        likeCount = 234,
        commentCount = 18,
        isLiked = false
    ),
    PostSearchResult(
        id = "post2",
        title = "Anyone free for a study group?",
        preview = "Looking for people to study for the biology midterm this weekend...",
        authorName = "Sarah Chen",
        timestamp = "5 hours ago",
        likeCount = 45,
        commentCount = 12,
        isLiked = true
    ),
    PostSearchResult(
        id = "post3",
        title = "New study app recommendations",
        preview = "Just tried these apps and they really helped improve my productivity...",
        authorName = "Jordan Lee",
        timestamp = "1 day ago",
        likeCount = 567,
        commentCount = 89,
        isLiked = false
    )
)

private fun getSampleUserResults() = listOf(
    UserSearchResult(
        id = "user1",
        name = "Alex Johnson",
        handle = "alexjohnson",
        bio = "CS Major | Study enthusiast | Coffee lover ☕",
        followersCount = 1250,
        isFollowing = false
    ),
    UserSearchResult(
        id = "user2",
        name = "Sarah Chen",
        handle = "sarahchen",
        bio = "Pre-med student passionate about biology and helping others learn",
        followersCount = 856,
        isFollowing = true
    ),
    UserSearchResult(
        id = "user3",
        name = "Jordan Lee",
        handle = "jordanlee",
        bio = "Engineering major | Maker | Always learning something new",
        followersCount = 2341,
        isFollowing = false
    )
)

private fun getSampleGroupResults() = listOf(
    GroupSearchResult(
        id = "group1",
        name = "STEM Study Circle",
        category = "Academic",
        description = "A collaborative group for students in Science, Technology, Engineering, and Math disciplines to study together.",
        memberCount = 347,
        isMember = false
    ),
    GroupSearchResult(
        id = "group2",
        name = "Language Exchange Club",
        category = "Languages",
        description = "Practice and improve language skills by connecting with native speakers and fellow learners.",
        memberCount = 203,
        isMember = true
    ),
    GroupSearchResult(
        id = "group3",
        name = "Morning Runners Society",
        category = "Fitness",
        description = "Join us for early morning runs around campus. All fitness levels welcome!",
        memberCount = 156,
        isMember = false
    )
)

private fun getSampleEventResults() = listOf(
    EventSearchResult(
        id = "event1",
        title = "Mid-semester Study Marathon",
        date = "March 15, 2024",
        time = "10:00 AM - 8:00 PM",
        location = "Student Center - Main Hall",
        attendeeCount = 145,
        isAttending = false
    ),
    EventSearchResult(
        id = "event2",
        title = "Campus Welcome Fair",
        date = "March 20, 2024",
        time = "12:00 PM - 5:00 PM",
        location = "Central Lawn",
        attendeeCount = 890,
        isAttending = true
    ),
    EventSearchResult(
        id = "event3",
        title = "Entrepreneurship Workshop",
        date = "March 22, 2024",
        time = "2:00 PM - 4:00 PM",
        location = "Business Building - Room 201",
        attendeeCount = 67,
        isAttending = false
    )
)