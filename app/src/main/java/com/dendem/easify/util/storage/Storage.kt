package com.dendem.easify.util.storage

interface Storage {
    fun setString(key: String, value: String?)
    fun getString(key: String): String?
}
