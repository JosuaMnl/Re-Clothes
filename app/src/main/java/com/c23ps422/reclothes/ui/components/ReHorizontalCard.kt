package com.c23ps422.reclothes.ui.components

import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReHorizontalCard(modifier: Modifier = Modifier) {
    Card(
        onClick = {}
    ) {

    }
}

@Preview
@Composable
fun ReHorizontalCardPreview() {
    ReClothesTheme {
        ReHorizontalCard()
    }
}