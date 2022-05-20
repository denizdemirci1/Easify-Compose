package com.dendem.easify.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dendem.easify.R

@Composable
fun PromoView(
    promoViewType: PromoViewType,
    title: String,
    description: String
) {
    when (promoViewType) {
        PromoViewType.FOR_LIST -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    color = MaterialTheme.colors.surface,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.surface,
                    text = description,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }

        PromoViewType.FOR_CAROUSEL -> {
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .border(BorderStroke(2.dp, colorResource(id = R.color.spotifyGreen)))
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = description,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

enum class PromoViewType {
    FOR_CAROUSEL, FOR_LIST
}
