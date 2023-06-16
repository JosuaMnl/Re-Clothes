package com.c23ps422.reclothes.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.c23ps422.reclothes.helper.UiState

@Composable
fun UserScreen() {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.provideFactory(context)
    )

    Profile(userViewModel)
}

@Composable
fun Profile(userViewModel: UserViewModel) {
    userViewModel.getUser()
    userViewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val userProfileResponse = uiState.data
                ProfileContent(userInfo = userProfileResponse)
            }
            is UiState.Error -> {
                val errorMessage = uiState.errorMessage
                Log.d("UserScreen Error Message", errorMessage)
            }
        }
    }
}