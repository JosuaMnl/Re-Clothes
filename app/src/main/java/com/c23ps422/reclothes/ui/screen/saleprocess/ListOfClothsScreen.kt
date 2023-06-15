package com.c23ps422.reclothes.ui.screen.saleprocess

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity.ClothesIdentityViewModel
import com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity.getFabricName
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

data class ClothesItem(
    val clothImage: String,
    val type: String,
    val description: String,
    val id: String
)

@Composable
fun ListOfClothes(
    onClick: () -> Unit,
    clothesIdentityViewModel: ClothesIdentityViewModel,
    navController: NavController
) {
    val context = LocalContext.current

//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val clothesIdentityViewModel: ClothesIdentityViewModel? =
//        backStackEntry?.let { entry ->
//            viewModel(factory = ClothesIdentityViewModel.provideFactory(context), viewModelStoreOwner = entry)
//        }

    clothesIdentityViewModel.clothes.collectAsState().value.let { data ->
        Log.d("ListOfClothes", "ListOfClothes: $data")
        ClothItemContent(onClick = onClick, listOfClothes = data)

    }
}

@Composable
fun ClothItemContent(listOfClothes: List<ClothesItem>, onClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(24.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 12.dp),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                    ),
                    text = "Daftar pakaian bekas yang ingin dijual :"
                )
            }
            items(listOfClothes, key = { key -> key.id }) { clothes ->
                ClothItem(
                    image = clothes.clothImage,
                    type = clothes.type,
                    description = clothes.description
                )
                Divider()
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = onClick) {
                        Text(text = "Tambah Pakaian")
                    }
                    Button(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = {}) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    }
}

@Composable
fun ClothItem(
    image: String,
    type: String,
    description: String
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp),
                model = image,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = type, style = MaterialTheme.typography.h6)
                Text(text = description, style = MaterialTheme.typography.subtitle1)

            }
        }
    }

}

@Preview
@Composable
fun ClothItemContentPreview() {
    val listOfClothes = listOf(
        ClothesItem(
            id = "1",
            type = "Jacket",
            description = "A warm winter jacket",
            clothImage = "imageUrl1"
        ),
        ClothesItem(
            id = "2",
            type = "Pants",
            description = "Comfortable cotton pants",
            clothImage = "imageUrl2"
        )
    )
    ReClothesTheme {
        ClothItemContent(
            listOfClothes = listOfClothes,
            onClick = {}
        )
    }
}