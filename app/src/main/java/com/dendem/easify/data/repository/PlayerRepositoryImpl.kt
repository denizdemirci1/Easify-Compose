package com.dendem.easify.data.repository

import com.dendem.easify.data.remote.SpotifyApi
import com.dendem.easify.data.remote.dto.HistoryDTO
import com.dendem.easify.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val spotifyApi: SpotifyApi
) : PlayerRepository {

    override suspend fun getUserHistory(limit: Int): HistoryDTO {
        return spotifyApi.fetchRecentlyPlayed(limit)
    }
}
