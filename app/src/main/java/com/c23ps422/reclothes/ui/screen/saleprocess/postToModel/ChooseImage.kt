package com.c23ps422.reclothes.ui.screen.saleprocess.postToModel

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.components.ReOutlinedButton
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ChooseImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
    ) {
        Image(painter = painterResource(id = R.drawable.photographer), contentDescription = null)
        Text(
            text = stringResource(R.string.ci_title),
            textAlign = TextAlign.Center,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tsukifell, awalnya OutlineButton dibuat disini, kubuat component baru dan pakai statelessnya
            ReOutlinedButton(
                onClick = onClick,
                text = stringResource(R.string.ci_camera),
                icon = R.drawable.camera
            )
            ReOutlinedButton(
                onClick = {
                    Toast.makeText(context, R.string.in_progress, Toast.LENGTH_SHORT).show()
                },
                text = stringResource(R.string.ci_gallery),
                icon = R.drawable.gallery
            )
        }

        Text(
            text = stringResource(R.string.ci_note),
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            ),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChooseImagePreview() {
    ReClothesTheme {
        ChooseImage(onClick = {})
    }
}