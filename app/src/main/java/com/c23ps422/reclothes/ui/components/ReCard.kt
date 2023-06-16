package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import com.c23ps422.reclothes.R

@Composable
fun ReCard(
    photoUrl: String,
    title: String,
    additionalDesc: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(android.graphics.Color.parseColor(stringResource(R.string.reclothes_dark_yellow)))
    ) {
        Column {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            )

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp),
                text = title,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                text = additionalDesc,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.Light
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReCardPreview() {
    ReClothesTheme {
        ReCard(
            "https://img.freepik.com/free-vector/hand-drawn-environmental-protection-illustration_23-2150089574.jpg?w=740&t=st=1686207756~exp=1686208356~hmac=09ad13f57384ffd42585b423e990bd45c57c15d49c27d534d85c93e03e823af3",
            "Stylish Bag",
            "2.5 M Views"
        )
    }
}