package com.dendem.easify.presentation.favorites

import com.dendem.easify.common.Result
import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.data.remote.dto.TopTracksDTO
import java.lang.Exception


data class FavoritesState(
    val isLoading: Boolean = false,
    val topTracksData: TopTracksDTO? = null,
    val topArtistsData: TopArtistsDTO? = null,
    val error: Result.Error<Exception>? = null
)