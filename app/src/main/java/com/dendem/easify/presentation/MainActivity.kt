package com.dendem.easify.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.dendem.easify.BuildConfig
import com.dendem.easify.R
import com.dendem.easify.common.Constants
import com.dendem.easify.presentation.ui.theme.EasifyTheme
import com.spotify.sdk.android.auth.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel
    private var isTokenValid = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasifyTheme {
                viewModel = hiltViewModel()
                isTokenValid = remember { mutableStateOf(viewModel.getToken().isNullOrEmpty().not()) }
                val navController = rememberNavController()
                if (isTokenValid.value) {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationView(navController = navController)
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavigationGraph(navController)
                        }
                    }
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black
                    ) {}
                    requestToken()
                }
            }
        }
    }

    fun requestToken() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthorizationClient.getResponse(resultCode, data)
        if (response.error != null && response.error.isNotEmpty()) {
            val error = response.error
        } else if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            viewModel.setToken(response.accessToken)
            isTokenValid.value = true
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
