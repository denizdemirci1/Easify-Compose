package com.dendem.easify.presentation.home

import com.dendem.easify.common.Result
import com.dendem.easify.data.remote.dto.HistoryDTO
import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.data.remote.dto.TopTracksDTO
import java.lang.Exception

data class HomeHistoryState(
    val isLoading: Boolean = false,
    val data: HistoryDTO? = null,
    val error: Result.Error<Exception>? = null
)

data class HomeFavoriteArtistsState(
    val isLoading: Boolean = false,
    val topArtistsData: TopArtistsDTO? = null,
    val error: Result.Error<Exception>? = null
)

data class HomeFavoriteTracksState(
    val isLoading: Boolean = false,
    val topTracksData: TopTracksDTO? = null,
    val error: Result.Error<Exception>? = null
)
