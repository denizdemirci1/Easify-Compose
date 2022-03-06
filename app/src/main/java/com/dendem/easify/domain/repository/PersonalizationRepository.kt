package com.dendem.easify.domain.repository

import com.dendem.easify.data.remote.dto.TopArtistsDTO
import com.dendem.easify.data.remote.dto.TopTracksDTO

interface PersonalizationRepository {

    suspend fun getTopTracks(timeRange: String, limit: Int): TopTracksDTO

    suspend fun getTopArtists(timeRange: String, limit: Int): TopArtistsDTO
}
