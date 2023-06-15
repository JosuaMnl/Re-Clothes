package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.R

@Composable
fun ReOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: Int,
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color(
                android.graphics.Color.parseColor(
                    stringResource(R.string.reclothes_dark_green)
                )
            ), contentColor = Color.White
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )
            Text(text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReOutlinedButtonPreview() {
    ReOutlinedButton(onClick = {}, text = "Camera", icon = R.drawable.camera)
}
