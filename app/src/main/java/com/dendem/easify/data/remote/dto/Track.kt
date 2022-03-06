package com.dendem.easify.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Track(
    val album: Album,
    val artists: List<Artist>,
    @SerializedName("duration_ms")
    val duration: Int,
    val explicit: Boolean,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrl,
    val href: String,
    val id: String,
    @SerializedName("is_local")
    val isLocal: Boolean,
    val name: String,
    val popularity: Int,
    @SerializedName("preview_url")
    val previewUrl: String,
    @SerializedName("track_number")
    val trackNumber: Int,
    val type: String,
    val uri: String
)
