package com.c23ps422.reclothes.ui.screen.saleprocess.postToModel

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.screen.login.dataStore
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

@Composable
fun PreviewTakenImage(
    pref: ReClothesPreference,
    photo: File?,
    modifier: Modifier = Modifier
) {
    var idCloth by rememberSaveable { mutableStateOf("") }

    // Get ID Cloth from DataStore for Post Cloth Image to API
    LaunchedEffect(key1 = Unit) {
        idCloth = pref.getId().firstOrNull().toString()
    }

    val context = LocalContext.current

    val postToModelViewModel: PostToModelViewModel =
        viewModel(factory = PostToModelViewModel.provideFactory(context))

    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(
                    onClick = { /* TODO: Handle navigate back */ },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.your_clothes_preview),
                    modifier = Modifier.padding(start = 16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            ) {
                if (photo != null) {
                    val photoUri = Uri.fromFile(photo)
                    Image(
                        painter = rememberAsyncImagePainter(photoUri),
                        contentDescription = "Preview taken image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ReButtonFullRounded(
                    text = "Continue",
                    onClick = {
//                        Log.d("PreviewTakenImage", idCloth)
                        if (photo != null) {
                            postToModelViewModel.createClothImage(photo, idCloth)
                        }
                    })
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(50.dp),
                    onClick = { /* TODO: Handle cancel button click */ }){
                    Text(text = "Cancel")
                }
            }
        }
    }

    postToModelViewModel.uiState.collectAsState().value.let {uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                Log.d("PreviewTakenImage", "FotoModelUrl: ${uiState.data.data.defectsImageUrl}")
                Toast.makeText(context, uiState.data.meta.status, Toast.LENGTH_SHORT).show()
            }

            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TakenImagePreview() {
    PreviewTakenImage(
        photo = null,
        pref = ReClothesPreference.getInstance(LocalContext.current.dataStore)
    )
}
