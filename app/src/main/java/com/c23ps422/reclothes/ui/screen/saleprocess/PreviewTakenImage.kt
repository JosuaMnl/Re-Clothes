package com.c23ps422.reclothes.ui.screen.saleprocess

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded

@Composable
fun PreviewTakenImage(
    photoUri: Uri?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = { /* TODO: Handle navigate back */ },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.your_clothes_preview),
                    modifier = Modifier.padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                if (photoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(photoUri),
                        contentDescription = "Preview taken image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ReButtonFullRounded(
                    text = "Continue",
                    onClick = { /* TODO: Handle continue button click */ })
                Spacer(modifier = Modifier.size(8.dp))
                ReButtonFullRounded(
                    text = "Cancel",
                    onClick = { /* TODO: Handle cancel button click */ })
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TakenImagePreview() {
    PreviewTakenImage(photoUri = Uri.parse("file:///storage/emulated/0/Android/media/com.c23ps422.reclothes/ReClothes/2023-06-13-17-30-49-935.jpg"))
}
