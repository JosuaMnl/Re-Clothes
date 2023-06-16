package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import android.widget.Toast
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
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity.ClothesIdentityViewModel
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

data class ClothesItem(
    val clothImage: String,
    val type: String,
    val description: String,
    val id: String
)

@Composable
fun ListOfClothes(
    createTransactionItemViewModel: CreateTransactionItemViewModel,
    createTransactionViewModel: CreateTransactionViewModel,
    clothesIdentityViewModel: ClothesIdentityViewModel,
    navController: NavController
) {
    clothesIdentityViewModel.clothes.collectAsState().value.let { data ->
        ClothItemContent(
            createTransactionItemViewModel = createTransactionItemViewModel,
            createTransactionViewModel = createTransactionViewModel,
            listOfClothes = data,
            navController = navController
        )
    }
}

@Composable
fun ClothItemContent(
    createTransactionItemViewModel: CreateTransactionItemViewModel,
    createTransactionViewModel: CreateTransactionViewModel,
    listOfClothes: List<ClothesItem>,
    navController: NavController
) {
    var transactionId by remember { mutableStateOf("") }

    val context = LocalContext.current
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
                    text = stringResource(id = R.string.lio_list_of_clothes)
                )
            }
            items(listOfClothes, key = { key -> key.id }) { clothes ->
                ClothItem(
                    image = clothes.clothImage,
                    type = clothes.type,
                    description = clothes.description,
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
                        shape = RoundedCornerShape(50.dp), onClick = {
                            Toast.makeText(context, R.string.in_progress, Toast.LENGTH_SHORT).show()
                        }) {
                        Text(text = stringResource(id = R.string.lio_add_clothes))
                    }
                    Button(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = {
                            createTransactionViewModel.createTransaction()
                        }) {
                        Text(text = stringResource(id = R.string.btn_continue))
                    }
                }
            }
        }
    }

    var transactionIsCreated by remember { mutableStateOf(false) }

    createTransactionViewModel.transactionId.collectAsState().value.let { id ->
        transactionId = id
    }

    createTransactionViewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            // Untuk saat ini, hanya bisa mengirim 1 item
            is UiState.Success -> {
                if (transactionId.isNotEmpty() && !transactionIsCreated) {
                    createTransactionItemViewModel.createTransactionItem(
                        transactionId,
                        listOfClothes[0].id
                    )
                    transactionIsCreated = true
                }
                LaunchedEffect(uiState) {
                    navController.navigate(Screen.DataAllClothes.route)
                }
            }

            is UiState.Error -> {
                Toast.makeText(context, stringResource(id = R.string.lio_failed_save_item), Toast.LENGTH_SHORT).show()
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
            createTransactionViewModel = viewModel(
                factory = CreateTransactionViewModel.provideFactory(
                    LocalContext.current
                )
            ),
            listOfClothes = listOfClothes,
            navController = rememberNavController(),
            createTransactionItemViewModel = viewModel(
                factory = CreateTransactionItemViewModel.provideFactory(
                    LocalContext.current
                )
            )
        )
    }
}