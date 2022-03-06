package com.dendem.easify.common

object Constants {

    const val BASE_URL = "https://api.spotify.com/"
    const val CONNECT_TIMEOUT = 10L
    const val WRITE_TIMEOUT = 1L
    const val READ_TIMEOUT = 20L

    val SCOPES = arrayOf(
        "user-top-read",
        "user-read-recently-played"
    )
}
