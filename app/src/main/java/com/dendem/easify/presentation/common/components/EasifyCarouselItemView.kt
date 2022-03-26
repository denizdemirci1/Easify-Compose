package com.dendem.easify.presentation.common.components

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.extensions.getContentDescription

@Composable
fun EasifyCarouselItemView(
    item: EasifyItem,
    position: Int,
    onItemClick: (EasifyItem) -> Unit
) {
    Column(
        modifier = Modifier.width(100.dp)
    ) {
        AsyncImage(
            model = item.images?.first()?.url.orEmpty(),
            contentDescription = item.getContentDescription(),
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column() {
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
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}
