package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.ui.components.ReMedal

@Composable
fun MedalsScreen(modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(text = "Medals", fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(bottom = 4.dp))
        }
        items(6) {
            ReMedal(medalName = "Medal Name Here", medalDescription = "Lorem ipsum dolor sit amet", progressValue = 0.4f)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MedalsScreenPreview() {
    MedalsScreen()
}