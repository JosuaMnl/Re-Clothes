package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.formatMoney
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.components.ReStatus

@Composable
fun TransactionStatus(
    updateTransactionViewModel: UpdateTransactionViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onBackHome: () -> Unit
) {
    var status by rememberSaveable { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    updateTransactionViewModel.transaction.collectAsState().value.let { data ->
        price = formatMoney(data.price)
        weight = data.weight
        quantity = data.quantity
        date = data.date
        status = data.status
    }

    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxHeight(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        ) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.status), style = TextStyle(fontSize = 20.sp))
            Spacer(modifier = Modifier.padding(16.dp))
            ReStatus(
                date = date,
                statusName = "$status",
                description = "Pesanan berhasil dibuat"
            )
        }
        ReButtonFullRounded(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "Kembali ke halaman Home",
            onClick = onBackHome
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun TransactionStatusPreview() {
//    TransactionStatus(onBackPressed = {}, onBackHome = {})
//}