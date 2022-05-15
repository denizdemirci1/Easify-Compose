package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dendem.easify.domain.model.EasifyItem

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
