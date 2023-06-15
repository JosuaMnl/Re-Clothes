package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.R
import kotlinx.coroutines.delay

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