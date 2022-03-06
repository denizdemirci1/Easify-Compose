package com.dendem.easify.util.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(
    @ApplicationContext context: Context
) : Storage {

    private val sharedPreferences = context.getSharedPreferences("Easify", Context.MODE_PRIVATE)

    override fun setString(key: String, value: String?) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String? {
        return if (sharedPreferences.contains(key)) {
            sharedPreferences.getString(key, "")
        } else {
            null
        }
    }
}
