package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    LazyColumn(modifier.padding(horizontal = 14.dp)) {
        item {
            Text(text = "ReClothes", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(64.dp))
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Change this later on",
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = stringResource(R.string.welcome_reclothes),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.welcome_description),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            ReButtonFullRounded(text = stringResource(R.string.welcome_login), onClick = { })
            Spacer(modifier = Modifier.height(16.dp))
            ReButtonFullRounded(text = stringResource(R.string.welcome_register), onClick = { })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomePreview() {
    Welcome()
}