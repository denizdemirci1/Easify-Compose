package com.dendem.easify.extensions

import com.dendem.easify.data.remote.dto.History
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType

fun History.toEasifyItem(): EasifyItem {
    return EasifyItem(
        itemType = EasifyItemType.TRACK,
        trackName = this.track.name,
        artistName = this.track.artists.firstOrNull()?.name,
        albumName = this.track.album.name,
        images = this.track.album.images
    )
}
