package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ReMedals(modifier: Modifier = Modifier) {
    LazyColumn(modifier.padding(horizontal = 14.dp)){
        item{
            Text("Achievements")
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable()
fun ReMedalsPreview() {

}