package com.dendem.easify.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.common.Constants
import com.dendem.easify.common.Result
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.use_case.favorites.GetFavoriteArtistsUseCase
import com.dendem.easify.domain.use_case.favorites.GetFavoriteTracksUseCase
import com.dendem.easify.extensions.withPromo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteArtistsUseCase: GetFavoriteArtistsUseCase,
    private val getFavoriteTracksUseCase: GetFavoriteTracksUseCase,
    private val billingHelper: BillingHelper
) : ViewModel() {

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state

    private var isPremiumUser = false

    init {
        viewModelScope.launch {
            billingHelper.isPurchased(Constants.PREMIUM_ACCOUNT)
                .collectLatest { isPremium ->
                    isPremiumUser = isPremium
                    if (isPremiumUser) {
                        getTopTracks(TimeRange.SIX_MONTHS, Constants.PREMIUM_LIMIT)
                        getTopArtists(TimeRange.SIX_MONTHS, Constants.PREMIUM_LIMIT)
                    } else {
                        getTopTracks(TimeRange.SIX_MONTHS, Constants.FREE_LIMIT)
                        getTopArtists(TimeRange.SIX_MONTHS, Constants.FREE_LIMIT)
                    }
                }
        }
    }

    fun retry() {
        if (isPremiumUser) {
            getTopTracks(TimeRange.SIX_MONTHS, Constants.PREMIUM_LIMIT)
            getTopArtists(TimeRange.SIX_MONTHS, Constants.PREMIUM_LIMIT)
        } else {
            getTopTracks(TimeRange.SIX_MONTHS, Constants.FREE_LIMIT)
            getTopArtists(TimeRange.SIX_MONTHS, Constants.FREE_LIMIT)
        }
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
}

enum class TimeRange(val value: String) {
    LIFETIME("long_term"),
    SIX_MONTHS("medium_term"),
    MONTHLY("short_term")
}
