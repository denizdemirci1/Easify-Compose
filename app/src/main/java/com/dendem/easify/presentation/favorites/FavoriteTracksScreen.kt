package com.dendem.easify.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.common.components.EasifyListItemView
import com.dendem.easify.presentation.common.components.LoadingView

@Composable
fun FavoriteTracksScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    if (state.isLoading) {
        LoadingView()
    }
    if (state.error != null) {
        HandleError(state.error, viewModel)
    }
    if (state.topTracksData != null) {
        val items = state.topTracksData.items.map { it.toEasifyItem() }
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            verticalArrangement = Arrangement.spacedBy((-8).dp)
        ) {
            itemsIndexed(items) { index, item ->
                EasifyListItemView(
                    item = item,
                    position = index,
                    indicatorText = "#${index + 1}",
                    onItemClick = { }
                )
            }
        }
    }
}
