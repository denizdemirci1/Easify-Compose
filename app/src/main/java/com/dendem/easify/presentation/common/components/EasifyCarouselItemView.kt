package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dendem.easify.R
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.extensions.getContentDescription
import com.dendem.easify.presentation.ui.theme.EasifyTheme

@Composable
fun EasifyCarouselItemView(
    item: EasifyItem,
    position: Int,
    onItemClick: (EasifyItem) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick.invoke(item) }
    ) {
        when (item.itemType) {
            EasifyItemType.PROMO -> {
                Row {
                    PromoView(
                        promoViewType = PromoViewType.FOR_CAROUSEL,
                        title = item.trackName!!,
                        description = item.artistName!!
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            else -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PlaceHolderView(
                        20.dp,
                        20.dp,
                        item.getContentDescription(),
                        R.drawable.ic_spotify_green
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "Spotify",
                        color = MaterialTheme.colors.surface,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.overline
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (item.images.isNullOrEmpty()) {
                    PlaceHolderView(
                        100.dp,
                        100.dp,
                        item.getContentDescription(),
                        R.drawable.ic_favorites,
                        R.color.spotifyBlack
                    )
                } else {
                    AsyncImage(
                        model = item.images.first().url,
                        contentDescription = item.getContentDescription(),
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .width(100.dp)
                ) {
                    item.trackName?.let { trackName ->
                        Text(
                            text = trackName,
                            color = MaterialTheme.colors.surface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.subtitle1
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item.artistName?.let { artistName ->
                        Text(
                            text = artistName,
                            color = MaterialTheme.colors.surface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.subtitle2
                        )
                    }
                }
            }
        }
    }
}

@Preview("carousel item view")
@Composable
fun EasifyCarouselItemViewPreview() {
    EasifyTheme {
        EasifyCarouselItemView(
            EasifyItem(EasifyItemType.TRACK, "Heathens", "Aurora"),
            1,
            {}
        )
    }
}
