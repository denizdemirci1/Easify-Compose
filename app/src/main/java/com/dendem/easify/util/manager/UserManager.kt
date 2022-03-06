package com.dendem.easify.util.manager

import com.dendem.easify.util.storage.Storage
import javax.inject.Inject

class UserManager @Inject constructor(
    private val storage: Storage
) {

    companion object {
        const val TOKEN = "easify.token"
    }

    var token: String?
        get() = storage.getString(TOKEN)
        set(value) = storage.setString(TOKEN, value)
}
