package com.dendem.easify.presentation.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.R
import com.dendem.easify.extensions.toEasifyItem
import com.dendem.easify.presentation.MainActivity
import com.dendem.easify.presentation.common.components.EasifyListItemView
import com.dendem.easify.presentation.common.components.ErrorView
import com.dendem.easify.presentation.common.components.LoadingView
import com.dendem.easify.presentation.common.components.RetryView
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingView()
            state.error != null -> {
                if (state.error.code == 401) {
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()
                    RetryView(
                        description = stringResource(id = R.string.refresh_session_description),
                        buttonText = stringResource(id = R.string.refresh)
                    ) {
                        coroutineScope.launch {
                            (context as? MainActivity)?.requestToken()
                            viewModel.retry()
                        }
                    }
                } else {
                    ErrorView(state.error.message.orEmpty())
                }
            }
            state.data != null -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(state.data.items) { index, item ->
                        EasifyListItemView(
                            item = item.toEasifyItem(),
                            position = index,
                            onItemClick = {}
                        )
                    }
                }
            }
        }
    }
}
