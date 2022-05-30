package com.dendem.easify.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dendem.easify.BuildConfig
import com.dendem.easify.R
import com.dendem.easify.billing.BillingHelper
import com.dendem.easify.billing.BillingHelperImpl
import com.dendem.easify.common.Constants
import com.dendem.easify.presentation.ui.theme.EasifyTheme
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

    private var tokenRefreshedListener: (() -> Unit)? = null

    @Inject lateinit var billingHelper: BillingHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasifyTheme {
                viewModel = hiltViewModel()
                val state = viewModel.state.value
                val navController = rememberNavController()
                when {
                    state.error.isNullOrEmpty().not() -> {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = colorResource(id = R.color.spotifyBlack)
                        ) {
                            Text(
                                text = state.error.toString(),
                                color = Color.White
                            )
                        }
                    }
                    state.token.isNullOrEmpty() -> {
                        requestToken()
                    }
                    else -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationView(navController = navController)
                            }
                        ) { innerPadding ->
                            Box(
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                NavigationGraph(navController, billingHelper)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun requestToken() {
        val tokenRequest = AuthorizationRequest.Builder(
            BuildConfig.SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            getString(R.string.com_spotify_sdk_redirect_host)
        )
            .setShowDialog(false)
            .setScopes(Constants.SCOPES)
            .build()
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, tokenRequest)
    }

    fun setToken(
        token: String?,
        listener: () -> Unit
    ) {
        viewModel.setToken(token)
        tokenRefreshedListener = listener
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthorizationClient.getResponse(resultCode, data)
        if (response.error != null && response.error.isNotEmpty()) {
            viewModel.setError(response.error)
        } else if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            viewModel.setToken(response.accessToken)
            tokenRefreshedListener?.invoke()
        }
    }

    companion object {
        const val AUTH_TOKEN_REQUEST_CODE = 0x10
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EasifyTheme {
        Greeting("Android")
    }
}
