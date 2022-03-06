package com.dendem.easify.extensions

import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType

fun EasifyItem.getContentDescription() = when (this.itemType) {
    EasifyItemType.TRACK -> this.trackName
    EasifyItemType.ARTIST -> this.artistName
    EasifyItemType.ALBUM -> this.albumName
}
