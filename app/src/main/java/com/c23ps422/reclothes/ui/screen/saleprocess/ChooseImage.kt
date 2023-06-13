package com.c23ps422.reclothes.ui.screen.saleprocess

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ChooseImage(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
    ) {
        Image(painter = painterResource(id = R.drawable.photographer), contentDescription = null)
        Text(
            text = "Silahkan pilih gambar pakaian bekas \n" +
                    "dari kamera atau ambil dari galeri",
            textAlign = TextAlign.Center,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = {})
            {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.camera),
                        contentDescription = null
                    )
                    Text(text = "Camera")
                }
            }

            OutlinedButton(
                onClick = {}) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.gallery), contentDescription = null)
                    Text(text = "Gallery")
                }
            }
        }

        Text(
            text = "Note : Gambar yang dimasukkan hanya bisa satu",
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            ),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChooseImagePreview() {
    ReClothesTheme {
        ChooseImage()
    }
}