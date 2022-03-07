package com.dendem.easify.presentation.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.*
import kotlinx.coroutines.launch

@Composable
fun FavoriteTracksScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (state.isLoading) {
            LoadingView()
        }
        if (state.error != null) {
            if (state.error.code == 401) {
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
                ErrorView(state.error.message.orEmpty())
            }
        }
        if (state.topTracksData != null) {
            val items = state.topTracksData.items.map { it.toEasifyItem() }
            items.forEachIndexed { index, item ->
                EasifyListItemView(item = item, position = index, onItemClick = { })
            }
        }
    }
}
