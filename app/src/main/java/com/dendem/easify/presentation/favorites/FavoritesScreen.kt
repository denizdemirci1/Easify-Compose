package com.dendem.easify.presentation.favorites

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.presentation.favorites.components.FavoritesScreenType
import com.dendem.easify.presentation.favorites.components.FavoritesTabBar

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var selectedTab by remember { mutableStateOf(FavoritesScreenType.ARTISTS) }
    val titles = listOf(
        stringResource(id = R.string.artists),
        stringResource(id = R.string.tracks)
    )
    Scaffold(
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
