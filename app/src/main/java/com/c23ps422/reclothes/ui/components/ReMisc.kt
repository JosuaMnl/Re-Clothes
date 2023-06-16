package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.R

@Composable
fun ReMisc(
    modifier: Modifier = Modifier,
    name: String,
    description: String,
    icon: ImageVector,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color(android.graphics.Color.parseColor(stringResource(R.string.reclothes_skin))),
        modifier = Modifier.size(160.dp, 70.dp),
    ) {
        Box(modifier = modifier.padding(8.dp)) {
            Row(Modifier.align(Alignment.Center)) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.rmsc_icon_desc),
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Black,
                )
                Column(
                    Modifier.padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Medium,
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
}

@Preview(showBackground = true)
@Composable
fun ReMiscPreview() {
    ReMisc(name = "Pendapatan", description = "Rp. 500.000", icon = Icons.Default.Info)
}