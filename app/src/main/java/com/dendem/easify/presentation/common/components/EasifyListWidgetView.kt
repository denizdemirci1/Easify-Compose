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
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            overflow = TextOverflow.Ellipsis
        )
        items.forEachIndexed { index, item ->
            EasifyListItemView(item = item, position = index, onItemClick = onItemClick)
        }
    }
}