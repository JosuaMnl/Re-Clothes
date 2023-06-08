package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ReMedal(
    modifier: Modifier = Modifier,
    medalName: String,
    medalDescription: String,
    photoUrl: String,
    xp: String,
    progressValue: Float
) {
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "Medal Card",
                modifier.size(120.dp)
            )
            Column(
                Modifier.padding(start = 4.dp, end = 12.dp, top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = medalName,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = medalDescription,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(text = xp, style = MaterialTheme.typography.subtitle1)
                LinearProgressIndicator(
                    progress = progressValue,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReMedalCardPreview() {
    ReMedal(
        medalName = "Medal Number 1",
        medalDescription = "Lorem ipsum dolor sit amet",
        photoUrl = "https://pbs.twimg.com/media/Fu1ptGkaUAAvSLC.jpg",
        xp = "300XP",
        progressValue = 0.4f
    )
}