package com.dendem.easify.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dendem.easify.util.manager.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    private val _state = mutableStateOf(MainActivityState())
    val state: State<MainActivityState> = _state

    init {
        _state.value = MainActivityState(isLoading = true, token = null)
    }

    fun setToken(token: String?) {
        userManager.token = token
        _state.value = _state.value.copy(isLoading = false, token = token)
    }

    fun setError(error: String) {
        _state.value = _state.value.copy(isLoading = false, error = error, token = null)
    }
}

data class MainActivityState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val token: String? = null
)
