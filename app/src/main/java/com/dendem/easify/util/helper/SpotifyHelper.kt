package com.dendem.easify.util.helper

import android.content.pm.PackageManager
import javax.inject.Inject

class SpotifyHelper @Inject constructor(
    private val pm: PackageManager
) {

    fun isSpotifyInstalled(): Boolean {
        return try {
            pm.getPackageInfo("com.spotify.music", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
