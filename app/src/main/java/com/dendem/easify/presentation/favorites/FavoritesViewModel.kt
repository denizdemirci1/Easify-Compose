package com.dendem.easify.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendem.easify.common.Result
import com.dendem.easify.domain.use_case.favorites.GetFavoriteArtistsUseCase
import com.dendem.easify.domain.use_case.favorites.GetFavoriteTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase,
    private val getFavoriteTracksUseCase: GetFavoriteTracksUseCase
) : ViewModel() {

    private val _state = mutableStateOf(FavoritesState())
    val state: State<FavoritesState> = _state

    init {
        getTopTracks(TimeRange.MEDIUM_TERM, 3)
        getTopArtists(TimeRange.MEDIUM_TERM, 3)
    }

    fun retry() {
        getTopTracks(TimeRange.MEDIUM_TERM, 3)
        getTopArtists(TimeRange.MEDIUM_TERM, 3)
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
                    _state.value = FavoritesState(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        )
                    )
                }
                is Result.Loading -> {
                    _state.value = FavoritesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopArtists(timeRange: TimeRange, limit: Int) {
        getFavoriteArtistsUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = _state.value.copy(
                        topArtistsData = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Result.Error -> {
                    _state.value = FavoritesState(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        )
                    )
                }
                is Result.Loading -> {
                    _state.value = FavoritesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

enum class TimeRange(val value: String) {
    LONG_TERM("long_term"),
    MEDIUM_TERM("medium_term"),
    SHORT_TERM("short_term")
}