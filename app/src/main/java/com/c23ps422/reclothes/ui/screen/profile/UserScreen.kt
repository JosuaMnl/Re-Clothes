package com.c23ps422.reclothes.ui.screen.profile

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import com.c23ps422.reclothes.api.ApiConfig
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.screen.login.dataStore
import com.c23ps422.reclothes.ui.screen.medals.ProfileContent

@Composable
fun UserScreen() {
    val context = LocalContext.current
    val apiService = ApiConfig.getApiService(context)
    val dataStore: DataStore<Preferences> = remember { context.dataStore }
    val pref = ReClothesPreference.getInstance(dataStore)
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.provideFactory(apiService, pref, Application())
    )

    LaunchedEffect(Unit) {
        userViewModel.getUser()
    }

    Profile(userViewModel)
}

@Composable
fun Profile(userViewModel: UserViewModel) {
    val userProfileState by userViewModel.userProfileState.collectAsState()

    when (val state = userProfileState) {
        is UiState.Idle -> {}

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is UiState.Success -> {
            val userProfileResponse = state.data
            ProfileContent(userInfo = userProfileResponse)
        }
        is UiState.Error -> {
            val errorMessage = state.errorMessage
            Log.d("UserScreen Error Message", errorMessage)
        }
    }
}