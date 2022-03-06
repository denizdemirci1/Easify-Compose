package com.dendem.easify.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("album_type")
    val albumType: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val uri: String
)
