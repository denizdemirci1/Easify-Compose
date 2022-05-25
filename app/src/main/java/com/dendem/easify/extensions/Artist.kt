package com.dendem.easify.extensions

import com.dendem.easify.data.remote.dto.Artist
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType

fun Artist.toEasifyItem(): EasifyItem {
    return EasifyItem(
        itemType = EasifyItemType.ARTIST,
        artistName = this.name,
        images = this.images,
        uri = this.uri
    )
}
