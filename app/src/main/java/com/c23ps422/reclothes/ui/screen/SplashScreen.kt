package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.login.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    splashScreenFinished: Boolean,
    modifier: Modifier = Modifier,
    onSplashScreenFinish: () -> Unit,
) {
    if (!splashScreenFinished) {
        Column(
            modifier = modifier
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "ReClothes")
        }
        LaunchedEffect(key1 = Unit) {
            delay(1000)
            onSplashScreenFinish()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    val context = LocalContext.current
    SplashScreen(false) {

    }
}