package com.dendem.easify.data.remote.dto

data class TopTracksDTO(
    val items: List<Track>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total: Int
)
