package com.dendem.easify.data.remote

import com.dendem.easify.util.manager.UserManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SpotifyInterceptor @Inject constructor(
    private val userManager: UserManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val token: String? = userManager.token
        token?.let { builder.addHeader("Authorization", "Bearer $it") }

        val request = builder.build()
        return chain.proceed(request)
    }
}
