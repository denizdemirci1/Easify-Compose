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

    // SKU
    const val PREMIUM_ACCOUNT = "premium_account"

    const val DEFAULT_LIMIT = 20
    const val FREE_LIMIT = 5
    const val FREE_HISTORY_LIMIT = 10
    const val PREMIUM_HOME_HISTORY_LIMIT = 20
    const val HOME_PREMIUM_LIMIT = 10
    const val PREMIUM_LIMIT = 50
}
