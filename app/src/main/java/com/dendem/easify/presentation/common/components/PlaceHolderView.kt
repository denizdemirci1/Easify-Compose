package com.dendem.easify.presentation.common.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun PlaceHolderView(
    width: Dp,
    height: Dp,
    contentDescription: String?,
    @DrawableRes drawableId: Int,
    @ColorRes backgroundColorId: Int? = null
) {
    if (backgroundColorId != null) {
        Icon(
            painter = painterResource(id = drawableId),
            modifier = Modifier
                .width(width)
                .height(height)
                .background(colorResource(id = backgroundColorId)),
            contentDescription = contentDescription
        )
    } else {
        Image(
            painter = painterResource(id = drawableId),
            modifier = Modifier
                .width(width)
                .height(height),
            contentDescription = contentDescription
        )
    }
}
