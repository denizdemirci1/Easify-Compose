package com.dendem.easify.presentation.favorites

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dendem.easify.R
import com.dendem.easify.presentation.favorites.components.FavoritesScreenType
import com.dendem.easify.presentation.favorites.components.FavoritesTabBar

@Composable
fun FavoritesScreen() {
    var selectedTab by remember { mutableStateOf(FavoritesScreenType.ARTISTS) }
    val titles = listOf(
        stringResource(id = R.string.artists),
        stringResource(id = R.string.tracks)
    )
    Scaffold(
        modifier = Modifier.padding(),
        topBar = {
            FavoritesTabBar(
                titles = titles,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) {
        if (selectedTab == FavoritesScreenType.ARTISTS) {
            FavoriteArtistsScreen()
        } else if (selectedTab == FavoritesScreenType.TRACKS) {
            FavoriteTracksScreen()
        }
    }
}
