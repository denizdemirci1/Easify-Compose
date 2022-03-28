package com.dendem.easify.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.common.Result
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyListItemView
import com.dendem.easify.presentation.common.components.ErrorView
import com.dendem.easify.presentation.common.components.LoadingView
import com.dendem.easify.presentation.common.components.RetryView
import kotlinx.coroutines.launch

@Composable
fun FavoriteArtistsScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    if (state.isLoading) {
        LoadingView()
    }
    if (state.error != null) {
        HandleError(state.error, viewModel)
    }
    if (state.topArtistsData != null) {
        val items = state.topArtistsData.items.map { it.toEasifyItem() }
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

@Composable
fun HandleError(
    error: Result.Error<Exception>,
    viewModel: FavoritesViewModel
) {
    if (error.code == 401) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        RetryView(
            description = stringResource(id = R.string.refresh_session_description),
            buttonText = stringResource(id = R.string.refresh)
        ) {
            coroutineScope.launch {
                (context as? MainActivity)?.requestToken()
                viewModel.retry()
            }
        }
    } else {
        ErrorView(error.message.orEmpty())
    }
}
