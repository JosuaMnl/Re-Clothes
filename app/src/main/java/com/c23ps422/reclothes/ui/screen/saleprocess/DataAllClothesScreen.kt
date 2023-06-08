package com.c23ps422.reclothes.ui.screen.saleprocess

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.ui.components.ReTextField
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun DataAllClothesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(24.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Isilah data untuk mencakup seluruh pakaian bekas yang ingin dijual dibawah ini :")
        ReTextField(value = "", onValueChange = {}, label = "Jumlah Pakaian")
        ReTextField(value = "", onValueChange = {}, label = "Berat")
        ReTextField(value = "", onValueChange = {}, label = "Alamat")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = { }) {
                Text(text = "Cancel")
            }
            Button(onClick = {}) {
                Text(text = "Continue")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DataAllClothesScreenPreview() {
    ReClothesTheme {
        DataAllClothesScreen()
    }
}