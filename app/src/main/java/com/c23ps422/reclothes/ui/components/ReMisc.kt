package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReMisc(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.size(130.dp, 80.dp),
        backgroundColor = Color(android.graphics.Color.parseColor("#D4D3A8"))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier.padding(start = 4.dp, end = 12.dp, top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReMiscPreview() {
    ReMisc(name = "Pendapatan", description = "Rp. 500.000")
}