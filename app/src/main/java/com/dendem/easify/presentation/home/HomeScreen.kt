package com.dendem.easify.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.MainViewModel
import com.dendem.easify.presentation.common.components.*
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val historyState = viewModel.homeHistoryState.value
    val topArtistsState = viewModel.homeFavoriteArtistsState.value
    val topTracksState = viewModel.homeFavoriteTracksState.value

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.weight(1f)
            .background(Color.Green)) {
            when {
                topArtistsState.isLoading -> LoadingView()
                topArtistsState.error != null -> {
                    if (topArtistsState.error.code == 401) {
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
                        ErrorView(topArtistsState.error.message.orEmpty())
                    }
                }
                topArtistsState.topArtistsData != null -> {
                    EasifyCarouselWidgetView(
                        title = "Top Artists",
                        items = topArtistsState.topArtistsData.items.map { it.toEasifyItem() },
                        onItemClick = {}
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                topTracksState.isLoading -> LoadingView()
                topTracksState.error != null -> {
                    if (topTracksState.error.code == 401) {
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
                        ErrorView(topTracksState.error.message.orEmpty())
                    }
                }
                topTracksState.topTracksData != null -> {
                    EasifyCarouselWidgetView(
                        title = "Top Tracks",
                        items = topTracksState.topTracksData.items.map { it.toEasifyItem() },
                        onItemClick = {}
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                historyState.isLoading -> LoadingView()
                historyState.error != null -> {
                    if (historyState.error.code == 401) {
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
                        ErrorView(historyState.error.message.orEmpty())
                    }
                }
                historyState.data != null -> {
                    EasifyCarouselWidgetView(
                        title = "History",
                        items = historyState.data.items.map { it.toEasifyItem() },
                        onItemClick = {}
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(0.25f))
    }
}

