package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.formatMoney
import com.c23ps422.reclothes.ui.navigation.Screen

@Composable
fun PriceRecommendation(
    navController: NavController,
    updateTransactionViewModel: UpdateTransactionViewModel
) {
    var price by remember {
        mutableStateOf("")
    }
    var weight by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("")
    }

    updateTransactionViewModel.transaction.collectAsState().value.let { data ->
        price = formatMoney(data.price)
        weight = data.weight
        quantity = data.quantity
    }

    Scaffold(
        topBar = {
            IconButton(
                onClick = {
                          
                },
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(24.dp)
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.pr_detail),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = "Jumlah pakaian : $quantity pcs",
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        modifier = Modifier.size(10.dp)
                    )
                    Text(
                        text = "Berat pakaian : $weight kg",
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(64.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Rekomendasi Harga Awal :",
                        style = MaterialTheme.typography.h3.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "$price",
                        style = MaterialTheme.typography.h3.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.pr_note),
                    style = MaterialTheme.typography.h6.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = { }) {
                        Text(text = stringResource(id = R.string.btn_cancel))
                    }
                    Button(modifier = Modifier
                        .height(42.dp),
                        shape = RoundedCornerShape(50.dp), onClick = {
                            navController.navigate(Screen.TransactionStatus.route)
                        }) {
                        Text(text = stringResource(id = R.string.btn_continue))
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PriceRecommendationPreview() {
//    ReClothesTheme {
//        PriceRecommendation()
//    }
//}