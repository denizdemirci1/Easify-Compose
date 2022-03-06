package com.dendem.easify.extensions

import com.dendem.easify.data.remote.dto.Track
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType

fun Track.toEasifyItem(): EasifyItem {
    return EasifyItem(
        itemType = EasifyItemType.TRACK,
        trackName = this.name,
        artistName = this.artists.firstOrNull()?.name,
        albumName = this.album.name,
        images = this.album.images
    )
}
