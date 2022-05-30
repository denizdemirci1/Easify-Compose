package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.layout.*
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
import com.dendem.easify.extensions.getContentDescription
import com.dendem.easify.presentation.ui.theme.EasifyTheme

@Composable
fun EasifyListWidgetView(
    title: String,
    items: List<EasifyItem>,
    onItemClick: (EasifyItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.h2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        items.forEachIndexed { index, item ->
            EasifyListItemView(
                item = item,
                position = index,
                onItemClick = onItemClick
            )
        }
    }
}

@Preview("list widget view")
@Composable
fun EasifyListWidgetViewPreview() {
    EasifyTheme {
        EasifyListWidgetView(
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