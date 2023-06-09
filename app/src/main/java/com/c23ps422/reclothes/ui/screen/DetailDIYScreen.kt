package com.c23ps422.reclothes.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.viewmodel.DetailDIYViewModel
import com.c23ps422.reclothes.ui.viewmodel.ViewModelFactory

@Composable
fun DetailDIYScreen(
    diyId: Int,
    diyViewModel: DetailDIYViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideDIYRepository())
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
                    contentDescription = "Video thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ReButtonFullRounded(text = "Watch", onClick = { context.startActivity(intent) })
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DetailDIYPreview() {
//    DetailDIY(image = R.drawable.ic_launcher_background, title = "Stylish Bag", author = "Author name", description = "Lorem ipsum", onBackClick = { /*TODO*/ })
//}