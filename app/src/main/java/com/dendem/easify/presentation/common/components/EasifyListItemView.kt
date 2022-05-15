package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dendem.easify.R
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.extensions.getContentDescription

@Composable
fun EasifyListItemView(
    item: EasifyItem,
    position: Int,
    indicatorText: String? = null,
    onItemClick: (EasifyItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onItemClick.invoke(item) },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (indicatorText != null && item.itemType != EasifyItemType.PROMO) {
                Text(
                    text = indicatorText,
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            when (item.itemType) {
                EasifyItemType.PROMO -> {
                    PromoView(
                        promoViewType = PromoViewType.FOR_LIST,
                        title = item.trackName!!,
                        description = item.artistName!!
                    )
                }
                else -> {
                    if (item.images.isNullOrEmpty()) {
                        PlaceHolderView(
                            60.dp,
                            60.dp,
                            item.getContentDescription(),
                            R.drawable.ic_favorites,
                            R.color.spotifyBlack
                        )
                    } else {
                        AsyncImage(
                            model = item.images.first().url,
                            contentDescription = item.getContentDescription(),
                            modifier = Modifier
                                .size(60.dp, 60.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        item.trackName?.let { trackName ->
                            Text(
                                text = trackName,
                                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                                color = MaterialTheme.colors.surface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.subtitle1
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item.artistName?.let { artistName ->
                            Text(
                                text = artistName,
                                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                                color = MaterialTheme.colors.surface,
                                fontWeight = FontWeight.Normal,
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    }
                }
            }
        }
    }
}
