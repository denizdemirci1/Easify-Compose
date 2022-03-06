package com.dendem.easify.domain.repository

import com.dendem.easify.data.remote.dto.HistoryDTO

interface PlayerRepository {

    suspend fun getUserHistory(): HistoryDTO

}