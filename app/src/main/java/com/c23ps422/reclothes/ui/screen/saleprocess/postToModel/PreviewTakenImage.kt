package com.c23ps422.reclothes.ui.screen.saleprocess.postToModel

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.helper.compressImage
import com.c23ps422.reclothes.pref.ReClothesPreference
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.auth.login.dataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun PreviewTakenImage(
    postToModelViewModel: PostToModelViewModel,
    navController: NavController,
    pref: ReClothesPreference,
    photo: File?,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCancel: () -> Unit
) {
    var idCloth by rememberSaveable { mutableStateOf("") }

    // Get ID Cloth from DataStore for Post Cloth Image to API
    LaunchedEffect(key1 = Unit) {
        idCloth = pref.getId().firstOrNull().toString()
    }

    // Compress the image file
    val compressedPhoto: File? by remember(photo) {
        mutableStateOf(compressImage(photo))
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
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

            if (photo != null) {
                val photoUri = Uri.fromFile(photo)
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = stringResource(R.string.pti_note),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.pti_note),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .height(42.dp),
                    shape = RoundedCornerShape(50.dp), onClick = onCancel
                ) {
                    Text(stringResource(R.string.btn_cancel))
                }
                Button(modifier = Modifier
                    .height(42.dp),
                    shape = RoundedCornerShape(50.dp), onClick = {
                        if (compressedPhoto != null) {
                            postToModelViewModel.createClothImage(compressedPhoto!!, idCloth)
                        }
                    }) {
                    Text(stringResource(R.string.pti_continue), textAlign = TextAlign.Justify)
                }
            }
        }
    }

    val scope = rememberCoroutineScope()
    postToModelViewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                Toast.makeText(context, stringResource(id = R.string.pti_success), Toast.LENGTH_SHORT).show()
                LaunchedEffect(uiState) {
                    scope.launch {
                        navController.navigate(Screen.ClothesIdentity.route)
                    }
                }
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
    val context = LocalContext.current
    PreviewTakenImage(
        postToModelViewModel =
        viewModel(factory = PostToModelViewModel.provideFactory(context)),
        navController = rememberNavController(),
        photo = null,
        pref = ReClothesPreference.getInstance(LocalContext.current.dataStore),
        onBackClick = {},
        onCancel = {}
    )
}
