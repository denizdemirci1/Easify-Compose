package com.dendem.easify.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HistoryDTO(
    val items: List<History>,
    val next: String,
    val cursors: Cursors,
    val limit: Int,
    val href: String
)

data class History(
    val track: Track,
    @SerializedName("played_at")
    val playedAt: String,
    val context: Context
)
