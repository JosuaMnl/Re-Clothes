package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReTransaction(
    modifier: Modifier = Modifier,
    idTransaction: String,
    status: String,
) {
    Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                Modifier.padding(start = 4.dp, end = 12.dp, top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = idTransaction,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = status,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReTransaction() {
    ReTransaction(
        idTransaction = "A3045567",
        status = "Ongoing"
    )
}