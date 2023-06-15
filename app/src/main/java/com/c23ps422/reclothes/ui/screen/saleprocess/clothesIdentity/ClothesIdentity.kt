package com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.model.response.ClothImage
import com.c23ps422.reclothes.model.response.CreateImageData
import com.c23ps422.reclothes.ui.components.ReTextField
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.saleprocess.postToModel.PostToModelViewModel
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import kotlinx.coroutines.launch

@Composable
fun ClothesIdentity(
    clothesIdentityViewModel: ClothesIdentityViewModel,
    navController: NavController,
    postToModelViewModel: PostToModelViewModel
) {
    val context = LocalContext.current

    postToModelViewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                val data = uiState.data.data
                ClothesIdentityContent(
                    clothesIdentityViewModel = clothesIdentityViewModel,
                    navController = navController,
                    createImageData = data
                )
            }

            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ClothesIdentityContent(
    clothesIdentityViewModel: ClothesIdentityViewModel,
    navController: NavController,
    createImageData: CreateImageData
) {
    val clothImage = createImageData.clothImage

    var description by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current
//    val clothesIdentityViewModel: ClothesIdentityViewModel =
//        viewModel(factory = ClothesIdentityViewModel.provideFactory(context))

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(24.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                    ),
                    text = "Berikut adalah kerusakan pakaianmu :"
                )
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    model = createImageData.defectsImageUrl,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Foto Kerusakan Pakaian"
                )
                Text(
                    text = "Isilah data dibawah ini :",
                    modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontSize = 18.sp,
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Jenis Bahan Pakaian : ")
                    Text(text = getFabricName(clothImage.fabricStatus))
                }
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(value = description, onValueChange = {
                    description = it
                }, label = "Description")
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = { }) {
                        Text(text = "Cancel")
                    }
                    Button(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = {
                            clothesIdentityViewModel.createCloth(
                                image = createImageData.defectsImageUrl,
                                cloth_image_id = clothImage.id,
                                type = getFabricName(clothImage.fabricStatus),
                                description = description,
                            )
                        }) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    }

    clothesIdentityViewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                LaunchedEffect(uiState) {
                    Toast.makeText(context, uiState.data.meta.status, Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.ListOfClothes.route)
                }
            }

            is UiState.Error -> {
                LaunchedEffect(uiState) {
                    Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

/**
 * Function to map fabric status codes to their respective fabric names.
 *
 * @param fabricStatus The fabric status code. This is an integer where each value corresponds to a different fabric type.
 * @return The name of the fabric corresponding to the provided status code. If the status code does not match any known fabric type, "Unknown" is returned.
 */
fun getFabricName(fabricStatus: Int): String {
    return when (fabricStatus) {
        0 -> "Cotton"       // 0 represents Cotton
        1 -> "Denim"        // 1 represents Denim
        2 -> "Fleece"       // 2 represents Fleece
        3 -> "Nylon"        // 3 represents Nylon
        4 -> "Polyester"    // 4 represents Polyester
        5 -> "Silk"         // 5 represents Silk
        6 -> "TerryCloth"   // 6 represents TerryCloth
        7 -> "Viscose"      // 7 represents Viscose
        8 -> "Wool"         // 8 represents Wool
        else -> "Unknown"   // Other numbers do not match any known fabric
    }
}

@Preview
@Composable
fun ClothesIdentityContentPreview() {
    val context = LocalContext.current
    ReClothesTheme {
        ClothesIdentityContent(
            clothesIdentityViewModel =
            viewModel(factory = ClothesIdentityViewModel.provideFactory(context)),
            navController = rememberNavController(),
            createImageData = CreateImageData(
                defectsImageUrl = "https://storage.googleapis.com/reclothes/users-defects-cloths/082a0c0feb5b5f22cb382e6cb5fd8b9d9175a810f08f3905093d3fea473ad16f.jpg",
                originalImageUrl = "https://storage.googleapis.com/reclothes/users-defects-cloths/082a0c0feb5b5f22cb382e6cb5fd8b9d9175a810f08f3905093d3fea473ad16f.jpg",
                clothImage = ClothImage(
                    id = "1234567890",
                    fabricStatus = 0,
                    updatedAt = "",
                    createdAt = "",
                    defectsProof = "",
                    userClothId = "",
                    originalImage = ""
                )
            )
        )
    }

}

//@Preview
//@Composable
//fun ClothesIdentityPreview() {
//    val context = LocalContext.current
//    ReClothesTheme {
//        ClothesIdentity(
//            postToModelViewModel = viewModel(factory = PostToModelViewModel.provideFactory(context))
//        )
//    }
//}