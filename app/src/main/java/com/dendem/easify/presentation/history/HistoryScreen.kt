package com.dendem.easify.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.common.Result
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyListWidgetView
import com.dendem.easify.presentation.common.components.ErrorView
import com.dendem.easify.presentation.common.components.LoadingView
import com.dendem.easify.presentation.common.components.RetryView
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
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
                        items = state.data.items.map { it.toEasifyItem() },
                        onItemClick = {}
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
