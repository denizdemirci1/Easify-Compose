package com.dendem.easify.extensions

import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType

fun EasifyItem.getContentDescription() = when (this.itemType) {
    EasifyItemType.TRACK -> this.trackName
    EasifyItemType.ARTIST -> this.artistName
    EasifyItemType.ALBUM -> this.albumName
    else -> ""
}

fun List<EasifyItem>.withPromo(
    title: String,
    description: String
): List<EasifyItem> {
    val newList = this.toMutableList()
    newList.add(
        EasifyItem(
            itemType = EasifyItemType.PROMO,
            trackName = title,
            artistName = description
        )
    )
    return newList
}
