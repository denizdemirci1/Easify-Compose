package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dendem.easify.domain.model.EasifyItem

@Composable
fun EasifyCarouselWidgetView(
    title: String,
    items: List<EasifyItem>,
    onItemClick: (EasifyItem) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow() {
            itemsIndexed(items) { index, item ->
                EasifyCarouselItemView(
                    item = item,
                    position = index,
                    onItemClick = {}
                )
            }
        }
    }
}
