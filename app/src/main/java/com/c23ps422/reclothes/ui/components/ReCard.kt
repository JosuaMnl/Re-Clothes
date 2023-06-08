package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReCard(
    image: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {},

        ) {
        Column {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Stylish Bag",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
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
            R.drawable.ic_launcher_background
        )
    }
}