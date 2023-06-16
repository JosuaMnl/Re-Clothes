package com.c23ps422.reclothes.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.pref.ReClothesPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    navController: NavController,
    pref: ReClothesPreference,
    userViewModel: UserViewModel,
) {
    Profile(navController, pref, userViewModel)
}

@Composable
fun Profile(
    navController: NavController,
    pref: ReClothesPreference,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current

//    var token by rememberSaveable {
//        mutableStateOf("")
//    }

//    LaunchedEffect(Unit) {
//        val token = pref.getToken().firstOrNull()
//        if (token != null && userViewModel.username.value.isEmpty()) {
//            userViewModel.getUser(token)
//        }
//    }

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
                ProfileContent(
                    navController = navController,
                    pref = pref,
                    userInfo = userProfileResponse
                )
            }

            is UiState.Error -> {
                Toast.makeText(context, stringResource(R.string.us_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}