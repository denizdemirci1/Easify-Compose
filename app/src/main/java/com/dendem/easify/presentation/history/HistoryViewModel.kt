package com.dendem.easify.presentation.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendem.easify.common.Result
import com.dendem.easify.domain.use_case.history.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HistoryState())
    val state: State<HistoryState> = _state

    init {
        getHistory()
    }

    fun retry() {
        getHistory()
    }

    private fun getHistory() {
        getHistoryUseCase().onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.value = HistoryState(
                        data = result.data?.let { history ->
                            history.copy(items= history.items.distinctBy { it.track.id })
                        }
                    )
                }
                is Result.Error -> {
                    _state.value = HistoryState(
                        error = Result.Error(
                            message = result.message.orEmpty(),
                            code = result.code
                        )
                    )
                }
                is Result.Loading -> {
                    _state.value = HistoryState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
