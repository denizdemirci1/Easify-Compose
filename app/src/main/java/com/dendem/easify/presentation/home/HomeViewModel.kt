package com.dendem.easify.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendem.easify.common.Constants.FREE_LIMIT
import com.dendem.easify.common.Result
import com.dendem.easify.domain.use_case.favorites.GetFavoriteArtistsUseCase
import com.dendem.easify.domain.use_case.favorites.GetFavoriteTracksUseCase
import com.dendem.easify.domain.use_case.history.GetHistoryUseCase
import com.dendem.easify.presentation.favorites.TimeRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase,
    private val getFavoriteTracksUseCase: GetFavoriteTracksUseCase
) : ViewModel() {

    private val _homeHistoryState = mutableStateOf(HomeHistoryState())
    val homeHistoryState: State<HomeHistoryState> = _homeHistoryState

    private val _homeFavoriteArtistsState = mutableStateOf(HomeFavoriteArtistsState())
    val homeFavoriteArtistsState: State<HomeFavoriteArtistsState> = _homeFavoriteArtistsState

    private val _homeFavoriteTracksState = mutableStateOf(HomeFavoriteTracksState())
    val homeFavoriteTracksState: State<HomeFavoriteTracksState> = _homeFavoriteTracksState

    init {
        getHistory(FREE_LIMIT)
        getTopArtists(TimeRange.SIX_MONTHS, FREE_LIMIT)
        getTopTracks(TimeRange.SIX_MONTHS, FREE_LIMIT)
    }

    fun retry() {
        getHistory(FREE_LIMIT)
        getTopArtists(TimeRange.SIX_MONTHS, FREE_LIMIT)
        getTopTracks(TimeRange.SIX_MONTHS, FREE_LIMIT)
    }

    private fun getHistory(limit: Int) {
        getHistoryUseCase(limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _homeHistoryState.value = HomeHistoryState(
                        data = result.data?.let { history ->
                            history.copy(items = history.items.distinctBy { it.track.id })
                        }
                    )
                }
                is Result.Error -> {
                    _homeHistoryState.value = HomeHistoryState(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        )
                    )
                }
                is Result.Loading -> {
                    _homeHistoryState.value = HomeHistoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopArtists(timeRange: TimeRange, limit: Int) {
        getFavoriteArtistsUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _homeFavoriteArtistsState.value = _homeFavoriteArtistsState.value.copy(
                        topArtistsData = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _homeFavoriteArtistsState.value = HomeFavoriteArtistsState(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        )
                    )
                }
                is Result.Loading -> {
                    _homeFavoriteArtistsState.value = HomeFavoriteArtistsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopTracks(timeRange: TimeRange, limit: Int) {
        getFavoriteTracksUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _homeFavoriteTracksState.value = _homeFavoriteTracksState.value.copy(
                        topTracksData = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _homeFavoriteTracksState.value = HomeFavoriteTracksState(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        )
                    )
                }
                is Result.Loading -> {
                    _homeFavoriteTracksState.value = HomeFavoriteTracksState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
