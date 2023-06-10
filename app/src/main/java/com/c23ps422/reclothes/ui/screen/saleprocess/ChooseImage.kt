package com.c23ps422.reclothes.ui.screen.saleprocess

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ChooseImage(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(top = 24.dp),
    ) {
        Image(painter = painterResource(id = R.drawable.photographer), contentDescription = null)
        Text(
            text = "Silahkan pilih gambar pakaian bekas \n" +
                    "dari kamera atau ambil dari galeri"
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {})
            {
                Text(text = "Camera")
            }

            OutlinedButton(
                onClick = {}) {
                Text(text = "Gallery")
            }
        }
        Text(text = "Note : Gambar yang dimasukkan hanya bisa satu")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChooseImagePreview() {
    ReClothesTheme {
        ChooseImage()
    }
}