package com.dendem.easify.data.repository

import com.dendem.easify.data.remote.SpotifyApi
import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.data.remote.dto.TopTracksDTO
import com.dendem.easify.domain.repository.PersonalizationRepository
import javax.inject.Inject

class PersonalizationRepositoryImpl @Inject constructor(
    private val spotifyApi: SpotifyApi
) : PersonalizationRepository {

    override suspend fun getTopTracks(timeRange: String, limit: Int): TopTracksDTO {
        return spotifyApi.fetchTopTracks(timeRange, limit)
    }

    override suspend fun getTopArtists(timeRange: String, limit: Int): TopArtistsDTO {
        return spotifyApi.fetchTopArtists(timeRange, limit)
    }
}
