package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.ui.components.ReStatus

@Composable
fun TransactionStatus(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    Box(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Status", style = TextStyle(fontSize = 20.sp))
            Spacer(modifier = Modifier.padding(16.dp))
            ReStatus(
                date = "21 May 2023",
                statusName = "On Progress",
                description = "Pesanana berhasil dibuat"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionStatusPreview() {
    TransactionStatus(onBackPressed = {})
}