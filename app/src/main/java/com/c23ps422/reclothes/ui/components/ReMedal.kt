package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.R

@Composable
fun ReMedal(modifier: Modifier = Modifier, medalName: String, medalDescription: String, progressValue: Float) {
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Medal Card"
            )
            Column(
                Modifier.padding(start = 4.dp, end = 12.dp, top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = medalName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = medalDescription, fontSize = 8.sp, overflow = TextOverflow.Ellipsis)
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
    ReMedal(medalName = "Medal Number 1", medalDescription = "Lorem ipsum dolor sit amet", progressValue = 0.4f)
}