package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.c23ps422.reclothes.ui.components.ReTextField

@Composable
fun Login(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LazyColumn(modifier = modifier.padding(horizontal = 14.dp)) {
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
                text = stringResource(R.string.register_message),
                fontWeight = FontWeight.Bold,
                fontSize =24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))
            ReTextField(value = email, onValueChange = {}, label = "Email")
            Spacer(modifier = Modifier.height(12.dp))
            ReTextField(value = password, onValueChange = {}, label = "Password")
            Spacer(modifier = Modifier.height(12.dp))
            ReButtonFullRounded(text = stringResource(R.string.welcome_register), onClick = { })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    Login()
}