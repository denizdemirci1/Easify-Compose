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
import com.dendem.easify.extensions.toEasifyItem

@Composable
fun EasifyCarouselWidgetView(
    title: String,
    items: List<EasifyItem>,
    onItemClick: (EasifyItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxSize()
        ) {
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