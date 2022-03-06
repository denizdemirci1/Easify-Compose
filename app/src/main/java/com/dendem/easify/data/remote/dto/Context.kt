package com.dendem.easify.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Context(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrl,
    val href: String,
    val type: String,
    val uri: String
)
