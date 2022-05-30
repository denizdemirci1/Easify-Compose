package com.dendem.easify.presentation.favorites

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.billing.BillingHelperImpl
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
        LazyColumn {
            itemsIndexed(items) { index, item ->
                EasifyListItemView(
                    item = item,
                    position = index,
                    indicatorText = "#${index + 1}",
                    onItemClick = { handleItemClick(context, billingHelper, it, viewModel) }
                )
            }
        }
    }
}

private fun handleItemClick(
    context: Context,
    billingHelper: BillingHelper,
    item: EasifyItem,
    viewModel: FavoritesViewModel
) {
    when (item.itemType) {
        EasifyItemType.PROMO -> handlePromoClick(context, billingHelper)
        EasifyItemType.ARTIST,
        EasifyItemType.TRACK,
        EasifyItemType.ALBUM-> viewModel.openOnSpotify(item.uri)
    }
}

private fun handlePromoClick(
    context: Context,
    billingHelper: BillingHelper
) {
    billingHelper.launchBillingFlow(context as MainActivity, Constants.PREMIUM_ACCOUNT)
}
