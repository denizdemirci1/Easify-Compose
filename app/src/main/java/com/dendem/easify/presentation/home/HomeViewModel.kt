package com.dendem.easify.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendem.easify.common.Constants.FREE_HISTORY_LIMIT
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

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getHistory(FREE_HISTORY_LIMIT)
        getTopArtists(TimeRange.SIX_MONTHS, FREE_LIMIT)
        getTopTracks(TimeRange.SIX_MONTHS, FREE_LIMIT)
    }

    fun retry() {
        getHistory(FREE_HISTORY_LIMIT)
        getTopArtists(TimeRange.SIX_MONTHS, FREE_LIMIT)
        getTopTracks(TimeRange.SIX_MONTHS, FREE_LIMIT)
    }

    private fun getHistory(limit: Int) {
        getHistoryUseCase(limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        historyData = result.data?.let { history ->
                            history.copy(items = history.items.distinctBy { it.track.id })
                        },
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        ),
                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopArtists(timeRange: TimeRange, limit: Int) {
        getFavoriteArtistsUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        topArtistData = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        ),
                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopTracks(timeRange: TimeRange, limit: Int) {
        getFavoriteTracksUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        topTracksData = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        ),
                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
