package com.dendem.easify.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.billing.BillingHelperImpl
import com.dendem.easify.common.Constants
import com.dendem.easify.common.Constants.FREE_HISTORY_LIMIT
import com.dendem.easify.common.Constants.FREE_LIMIT
import com.dendem.easify.common.Constants.PREMIUM_HOME_ARTISTS_LIMIT
import com.dendem.easify.common.Constants.PREMIUM_HOME_HISTORY_LIMIT
import com.dendem.easify.common.Constants.PREMIUM_HOME_TRACKS_LIMIT
import com.dendem.easify.common.Result
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.use_case.favorites.GetFavoriteArtistsUseCase
import com.dendem.easify.domain.use_case.favorites.GetFavoriteTracksUseCase
import com.dendem.easify.domain.use_case.history.GetHistoryUseCase
import com.dendem.easify.extensions.withPromo
import com.dendem.easify.presentation.favorites.TimeRange
import com.dendem.easify.util.helper.SpotifyHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase,
    private val getFavoriteTracksUseCase: GetFavoriteTracksUseCase,
    private val billingHelper: BillingHelper,
    private val spotifyHelper: SpotifyHelper
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private var isPremiumUser = false

    init {
        viewModelScope.launch {
            billingHelper.isPurchased(Constants.PREMIUM_ACCOUNT)
                .collectLatest { isPremium ->
                    isPremiumUser = isPremium
                    if (isPremiumUser) {
                        getHistory(PREMIUM_HOME_HISTORY_LIMIT)
                        getTopArtists(TimeRange.SIX_MONTHS, PREMIUM_HOME_ARTISTS_LIMIT)
                        getTopTracks(TimeRange.SIX_MONTHS, PREMIUM_HOME_TRACKS_LIMIT)
                    } else {
                        getHistory(FREE_HISTORY_LIMIT)
                        getTopArtists(TimeRange.SIX_MONTHS, FREE_LIMIT)
                        getTopTracks(TimeRange.SIX_MONTHS, FREE_LIMIT)
                    }
                }
        }
    }

    fun retry() {
        if (isPremiumUser) {
            getHistory(PREMIUM_HOME_HISTORY_LIMIT)
            getTopArtists(TimeRange.SIX_MONTHS, PREMIUM_HOME_ARTISTS_LIMIT)
            getTopTracks(TimeRange.SIX_MONTHS, PREMIUM_HOME_TRACKS_LIMIT)
        } else {
            getHistory(FREE_HISTORY_LIMIT)
            getTopArtists(TimeRange.SIX_MONTHS, FREE_LIMIT)
            getTopTracks(TimeRange.SIX_MONTHS, FREE_LIMIT)
        }
    }

    private fun getHistory(limit: Int) {
        getHistoryUseCase(limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            historyData = result.data?.let { historyDTO ->
                                historyDTO.copy(
                                    items = historyDTO.items.distinctBy { history ->
                                        history.track.id
                                    }
                                )
                            },
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> handleError(result.message.orEmpty(), result.code)
                is Result.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopArtists(timeRange: TimeRange, limit: Int) {
        getFavoriteArtistsUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            topArtistsData = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> handleError(result.message.orEmpty(), result.code)
                is Result.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTopTracks(timeRange: TimeRange, limit: Int) {
        getFavoriteTracksUseCase(timeRange.value, limit).onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            topTracksData = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> handleError(result.message.orEmpty(), result.code)
                is Result.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }.launchIn(viewModelScope)
    }

    private fun handleError(
        message: String,
        code: Int?
    ) {
        _state.update {
            it.copy(
                error = Result.Error(
                    message = message,
                    code = code
                ),
                isLoading = false
            )
        }
    }

    fun prepareItemsForView(
        items: List<EasifyItem>,
        title: String,
        description: String
    ): List<EasifyItem> {
        var newList = items
        if (!isPremiumUser) {
            newList = items.withPromo(title, description)
        }
        return newList
    }

    fun openOnSpotify(uri: String?) {
        spotifyHelper.openOnSpotify(uri)
    }
}
