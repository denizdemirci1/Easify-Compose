package com.dendem.easify.presentation.home

import com.dendem.easify.common.Result
import com.dendem.easify.data.remote.dto.HistoryDTO
import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.data.remote.dto.TopTracksDTO
import java.lang.Exception

data class HomeState(
    val isLoading: Boolean = false,
    val historyData: HistoryDTO? = null,
    val topArtistsData: TopArtistsDTO? = null,
    val topTracksData: TopTracksDTO? = null,
    val error: Result.Error<Exception>? = null
)
