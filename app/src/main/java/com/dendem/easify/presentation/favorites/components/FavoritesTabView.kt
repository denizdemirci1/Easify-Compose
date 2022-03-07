package com.dendem.easify.presentation.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import com.dendem.easify.R

@Composable
fun FavoritesTabBar(
    titles: List<String>,
    selectedTab: FavoritesScreenType,
    onTabSelected: (FavoritesScreenType) -> Unit,
    modifier: Modifier = Modifier
) {
    FavoritesTabBarView(
        modifier = modifier
    ) { tabBarModifier ->
        FavoritesTabs(
            modifier = tabBarModifier,
            titles = titles,
            selectedTab = selectedTab,
            onTabSelected = { newTab -> onTabSelected(FavoritesScreenType.values()[newTab.ordinal]) }
        )
    }
}

@Composable
fun FavoritesTabBarView(
    modifier: Modifier = Modifier,
    children: @Composable (Modifier) -> Unit
) {
    Row(modifier) {
        children(
            Modifier
        )
    }
}

@Composable
fun FavoritesTabs(
    modifier: Modifier = Modifier,
    titles: List<String>,
    selectedTab: FavoritesScreenType,
    onTabSelected: (FavoritesScreenType) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = modifier,
        indicator = @Composable { tabPositions ->
            TabRowDefaults.Indicator(
                color = colorResource(id = R.color.spotifyGreen),
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal])
            )
        },
    ) {
        titles.forEachIndexed { index, title ->
            val selected = index == selectedTab.ordinal
            Tab(
                modifier = Modifier
                    .height(48.dp)
                    .background(colorResource(id = R.color.spotifyBlack)),
                unselectedContentColor = Color.White,
                selectedContentColor = colorResource(id = R.color.spotifyGreen),
                selected = selected,
                onClick = { onTabSelected(FavoritesScreenType.values()[index]) }
            ) {
                Text(
                    text = title.uppercase(
                        ConfigurationCompat.getLocales(LocalConfiguration.current)[0]
                    )
                )
            }
        }
    }
}

enum class FavoritesScreenType {
    ARTISTS, TRACKS
}
