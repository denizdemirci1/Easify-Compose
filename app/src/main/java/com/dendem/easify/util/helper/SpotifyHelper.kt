package com.dendem.easify.util.helper

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import com.dendem.easify.R
import javax.inject.Inject

class SpotifyHelper @Inject constructor(
    private val context: Context,
    private val pm: PackageManager
) {

    private fun isSpotifyInstalled(): Boolean {
        return try {
            pm.getPackageInfo("com.spotify.music", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun openOnSpotify(
        uri: String?
    ) {
        if (isSpotifyInstalled()) {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(uri)).also {
                    it.flags = FLAG_ACTIVITY_NEW_TASK
                }
            )
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.download_spotify),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
