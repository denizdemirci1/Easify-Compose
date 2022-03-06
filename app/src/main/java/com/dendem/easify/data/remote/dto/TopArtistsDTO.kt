package com.dendem.easify.data.remote.dto

data class TopArtistsDTO(
    val items: List<Artist>,
    val total: Int,
    val limit: Int,
    val offset: Int,
    val previous: String?,
    val href: String,
    val next: String?
)
