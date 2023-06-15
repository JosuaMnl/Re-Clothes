package com.c23ps422.reclothes.ui.screen.saleprocess

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.model.response.ClothItem
import com.c23ps422.reclothes.model.response.CreateImageData
import com.c23ps422.reclothes.ui.components.ReDropdownMenuSelect
import com.c23ps422.reclothes.ui.screen.saleprocess.postToModel.PostToModelViewModel
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ClothesIdentity(
    postToModelViewModel: PostToModelViewModel
) {
    val context = LocalContext.current
    postToModelViewModel.uiState.collectAsState().value.let { uiState ->
        when(uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                val data = uiState.data.data
                ClothesIdentityContent(createImageData = data)
            }

            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ClothesIdentityContent(
    createImageData: CreateImageData
) {
    val clothImage = createImageData.clothImage

    LazyColumn(
        contentPadding = PaddingValues(24.dp)
    ) {
        item {
            Text(text = "Berikut adalah kerusakan pakaianmu :")
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = createImageData.defectsImageUrl,
                contentDescription = "Foto Kerusakan Pakaian")
            Text(text = "Isilah data dibawah ini :")
            Text(text = clothImage.id)
            Text(text = clothImage.fabricStatus.toString())
        }
    }
}

@Preview
@Composable
fun ClothesIdentityPreview() {
    val context = LocalContext.current
    ReClothesTheme {
        ClothesIdentity(
            postToModelViewModel = viewModel(factory = PostToModelViewModel.provideFactory(context))
        )
    }
}