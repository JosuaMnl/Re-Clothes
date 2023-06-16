package com.c23ps422.reclothes.ui.screen.diy

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded

@Composable
fun DetailDIYScreen(
    diyId: Int,
    diyViewModel: DetailDIYViewModel = viewModel(
        factory = DetailDIYViewModel.provideFactory()
    ),
    navigateBack: () -> Unit,
) {
    diyViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                diyViewModel.getDiyById(diyId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailDIYContent(
                    photoUrl = data.photoUrl,
                    title = data.title,
                    author = data.creator,
                    description = data.description,
                    videoUrl = data.videoUrl,
                    onBackClick = navigateBack
                )
            }

            is UiState.Error -> {}

            else -> {}
        }
    }
}

@Composable
fun DetailDIYContent(
    photoUrl: String,
    title: String,
    author: String,
    description: String,
    videoUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)) }
    val context = LocalContext.current

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = stringResource(R.string.dds_image_desc),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Surface(
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                        tint = Color.Black,
                        modifier = Modifier.clickable { onBackClick() }
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Text(
                    text = author,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ReButtonFullRounded(text = stringResource(R.string.dds_watch), onClick = { context.startActivity(intent) })
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DetailDIYPreview() {
//    DetailDIY(image = R.drawable.ic_launcher_background, title = "Stylish Bag", author = "Author name", description = "Lorem ipsum", onBackClick = { /*TODO*/ })
//}