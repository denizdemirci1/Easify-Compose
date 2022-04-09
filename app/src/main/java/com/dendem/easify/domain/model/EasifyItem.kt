package com.dendem.easify.domain.model

import com.dendem.easify.data.remote.dto.Image

data class EasifyItem(
    val itemType: EasifyItemType,
    val trackName: String? = null,
    val artistName: String? = null,
    val albumName: String? = null,
    val images: List<Image>? = null
)

enum class EasifyItemType {
    TRACK, ARTIST, ALBUM, PROMO
}
