package com.dendem.easify.presentation.favorites

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.common.Constants
import com.dendem.easify.common.Result
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyListItemView
import com.dendem.easify.presentation.common.components.ErrorView
import com.dendem.easify.presentation.common.components.LoadingView
import com.dendem.easify.presentation.common.components.RetryView
import kotlinx.coroutines.launch

@Composable
fun FavoriteArtistsScreen(
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
    if (state.topArtistsData != null) {
        val items = viewModel.prepareItemsForView(
            items = state.topArtistsData.items.map { it.toEasifyItem() },
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

@Composable
fun HandleError(
    error: Result.Error<Exception>,
    viewModel: FavoritesViewModel
) {
    if (error.code == 401) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        RetryView(
            description = stringResource(id = R.string.refresh_session_description),
            buttonText = stringResource(id = R.string.refresh)
        ) {
            coroutineScope.launch {
                (context as? MainActivity)?.apply {
                    setToken(null) {
                        viewModel.retry()
                    }
                }
            }
        }
    } else {
        ErrorView(error.message.orEmpty())
    }
}

private fun handleItemClick(item: EasifyItem) { }

private fun handlePromoClick(
    context: Context,
    billingHelper: BillingHelper
) {
    billingHelper.launchBillingFlow(context as MainActivity, Constants.PREMIUM_ACCOUNT)
}
