package com.dendem.easify.presentation.history

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.billing.BillingHelperImpl
import com.dendem.easify.common.Constants
import com.dendem.easify.common.Result
import com.dendem.easify.domain.model.EasifyItem
import com.dendem.easify.domain.model.EasifyItemType
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyListWidgetView
import com.dendem.easify.presentation.common.components.ErrorView
import com.dendem.easify.presentation.common.components.LoadingView
import com.dendem.easify.presentation.common.components.RetryView
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    billingHelper: BillingHelper,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    LazyColumn {
        item {
            Column {
                if (state.isLoading) {
                    LoadingView()
                }
                if (state.error != null) {
                    HandleError(state.error, viewModel)
                }
                if (state.data != null) {
                    EasifyListWidgetView(
                        title = stringResource(id = R.string.recently_listened),
                        items = viewModel.prepareItemsForView(
                            items = state.data.items.map { it.toEasifyItem() },
                            title = stringResource(id = R.string.upgrade_premium_title),
                            description = stringResource(id = R.string.upgrade_premium_desc)
                        ),
                        onItemClick = { handleItemClick(context, billingHelper, it, viewModel) }
                    )
                }
            }
        }
    }
}

@Composable
private fun HandleError(
    error: Result.Error<Exception>,
    viewModel: HistoryViewModel
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

private fun handleItemClick(
    context: Context,
    billingHelper: BillingHelper,
    item: EasifyItem,
    viewModel: HistoryViewModel
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
