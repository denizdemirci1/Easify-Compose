package com.dendem.easify.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.common.Result
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyCarouselWidgetView
import com.dendem.easify.presentation.common.components.ErrorView
import com.dendem.easify.presentation.common.components.LoadingView
import com.dendem.easify.presentation.common.components.RetryView
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LazyColumn() {
        item {
            Column {
                if (state.isLoading) {
                    LoadingView()
                }
                if (state.error != null) {
                    HandleError(state.error, viewModel)
                }
                if (state.topArtistData != null) {
                    EasifyCarouselWidgetView(
                        title = stringResource(id = R.string.top_artists),
                        items = state.topArtistData.items.map { it.toEasifyItem() },
                        onItemClick = {}
                    )
                }
                if (state.topTracksData != null) {
                    EasifyCarouselWidgetView(
                        title = stringResource(id = R.string.top_tracks),
                        items = state.topTracksData.items.map { it.toEasifyItem() },
                        onItemClick = {}
                    )
                }
                if (state.historyData != null) {
                    EasifyCarouselWidgetView(
                        title = stringResource(id = R.string.recently_listened),
                        items = state.historyData.items.map { it.toEasifyItem() },
                        onItemClick = {}
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HandleError(
    error: Result.Error<Exception>,
    viewModel: HomeViewModel
) {
    if (error.code == 401) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        RetryView(
            description = stringResource(id = R.string.refresh_session_description),
            buttonText = stringResource(id = R.string.refresh)
        ) {
            coroutineScope.launch {
                (context as? MainActivity)?.apply {
                    setToken(null) {
                        viewModel.retry()
                    }
                }
            }
        }
    } else {
        ErrorView(error.message.orEmpty())
    }
}
