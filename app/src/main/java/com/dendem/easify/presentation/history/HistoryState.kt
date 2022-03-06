package com.dendem.easify.presentation.history

import com.dendem.easify.common.Result
import com.dendem.easify.data.remote.dto.HistoryDTO
import java.lang.Exception

data class HistoryState(
    val isLoading: Boolean = false,
    val data: HistoryDTO? = null,
    val error: Result.Error<Exception>? = null
)
