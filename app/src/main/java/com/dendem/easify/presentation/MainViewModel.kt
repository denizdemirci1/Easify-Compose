package com.dendem.easify.presentation

import androidx.lifecycle.ViewModel
import com.dendem.easify.util.manager.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    fun setToken(token: String) {
        userManager.token = token
    }

    fun getToken() = userManager.token
}
