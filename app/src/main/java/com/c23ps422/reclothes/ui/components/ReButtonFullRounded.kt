package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ReButtonFullRounded(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(
                android.graphics.Color.parseColor(
                    "#27360B"
                )
            )
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = Color.White
        )
    }
}

@Preview
@Composable
fun ReButtonFullRoundedPreview() {
    ReClothesTheme {
        ReButtonFullRounded(
            text = "Continue With Google",
            onClick = {}
        )
    }
}