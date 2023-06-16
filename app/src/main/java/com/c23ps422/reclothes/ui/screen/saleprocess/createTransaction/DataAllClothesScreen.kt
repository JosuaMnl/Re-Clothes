package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.ui.components.ReTextField
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun DataAllClothesScreen(
    createTransactionViewModel: CreateTransactionViewModel,
    updateTransactionViewModel: UpdateTransactionViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    var transactionId by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }


    createTransactionViewModel.transactionId.collectAsState().value.let { id ->
        transactionId = id
    }

    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = stringResource(id = R.string.dac_fill_data))
        ReTextField(value = quantity, onValueChange = {
            quantity = it
        }, label = stringResource(id = R.string.dac_total_count_cloth), enabled = true)
        ReTextField(value = weight, onValueChange = {
            weight = it
        }, label = stringResource(id = R.string.dac_weight), enabled = true)
        ReTextField(value = address, onValueChange = {
            address = it
        }, label = stringResource(id = R.string.dac_address), enabled = true)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(modifier = Modifier
                .height(42.dp),
                shape = RoundedCornerShape(50.dp), onClick = {

                }) {
                Text(text = stringResource(id = R.string.btn_cancel))
            }
            Button(modifier = Modifier
                .height(42.dp),
                shape = RoundedCornerShape(50.dp), onClick = {
                    updateTransactionViewModel.updateTransaction(transactionId, weight, quantity, address)
                }) {
                Text(text = stringResource(id = R.string.btn_continue))
            }
        }
    }

    val context = LocalContext.current
    updateTransactionViewModel.uiState.collectAsState().value.let { uiState ->
        when(uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                LaunchedEffect(uiState) {
                    navController.navigate(Screen.PriceRecommendation.route)
                }
            }
            is UiState.Error -> {
                Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DataAllClothesScreenPreview() {
//    ReClothesTheme {
//        DataAllClothesScreen()
//    }
//}