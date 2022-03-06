package com.dendem.easify.presentation.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dendem.easify.presentation.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Text(text = viewModel.getToken().toString())
}
