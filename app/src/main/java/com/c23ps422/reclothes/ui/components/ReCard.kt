package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        modifier = modifier.width(90.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.Transparent,
        onClick = {},
        ) {
        Column {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = "Stylish Bag",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 8.sp,
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