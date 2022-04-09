package com.dendem.easify.presentation.favorites

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.common.Constants
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyListItemView
import com.dendem.easify.presentation.common.components.LoadingView

@Composable
fun FavoriteTracksScreen(
    billingHelper: BillingHelper,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    if (state.isLoading) {
        LoadingView()
    }
    if (state.error != null) {
        HandleError(state.error, viewModel)
    }
    if (state.topTracksData != null) {
        val items = viewModel.prepareItemsForView(
            items = state.topTracksData.items.map { it.toEasifyItem() },
            title = stringResource(id = R.string.upgrade_premium_title),
            description = stringResource(id = R.string.upgrade_premium_desc)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy((-8).dp)
        ) {
            itemsIndexed(items) { index, item ->
                EasifyListItemView(
                    item = item,
                    position = index,
                    indicatorText = "#${index + 1}",
                    onItemClick = { clickedItem ->
                        when (clickedItem.itemType) {
                            EasifyItemType.PROMO -> handlePromoClick(
                                context,
                                billingHelper
                            )
                            else -> handleItemClick(item)
                        }
                    }
                )
            }
        }
    }
}

private fun handleItemClick(item: EasifyItem) { }

private fun handlePromoClick(
    context: Context,
    billingHelper: BillingHelper
) {
    billingHelper.launchBillingFlow(context as MainActivity, Constants.PREMIUM_ACCOUNT)
}
