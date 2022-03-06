package com.dendem.easify.data.remote

import com.dendem.easify.data.remote.dto.HistoryDTO
import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.data.remote.dto.TopTracksDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyApi {

    @GET("/v1/me/player/recently-played")
    suspend fun fetchRecentlyPlayed(
        @Query("limit") limit: Int = 50
    ): HistoryDTO

    @GET("v1/me/top/artists")
    suspend fun fetchTopArtists(
        @Query("time_range") timeRange: String,
        @Query("limit") limit: Int = 50
    ): TopArtistsDTO

    @GET("v1/me/top/tracks")
    suspend fun fetchTopTracks(
        @Query("time_range") timeRange: String?,
        @Query("limit") limit: Int = 50
    ): TopTracksDTO
}
