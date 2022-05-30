package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dendem.easify.R
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.presentation.ui.theme.EasifyTheme

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
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(items) { index, item ->
                EasifyCarouselItemView(
                    item = item,
                    position = index,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Preview("carousel widget view")
@Composable
fun EasifyCarouselWidgetViewPreview() {
    EasifyTheme {
        EasifyCarouselWidgetView(
            "top tracks",
            listOf(
                EasifyItem(EasifyItemType.TRACK, "Heathens", "Aurora"),
                EasifyItem(EasifyItemType.TRACK, "Heathens", "Aurora"),
                EasifyItem(EasifyItemType.TRACK, "Heathens", "Aurora")
            ),
            {}
        )
    }
}
