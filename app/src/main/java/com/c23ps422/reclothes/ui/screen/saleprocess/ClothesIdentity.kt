package com.c23ps422.reclothes.ui.screen.saleprocess

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.components.ReDropdownMenuSelect
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ClothesIdentity() {
    val items = listOf("Option 1", "Option 2", "Option 3")
    var selectedItem by remember { mutableStateOf("Option 1") }

    LazyColumn(
        contentPadding = PaddingValues(24.dp)
    ) {
        item {
            Text(text = "Berikut adalah kerusakan pakaianmu :")
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
            Text(text = "Isilah data dibawah ini :")
            ReDropdownMenuSelect(text = "Pilih merek pakaian yang kamu jual",items = items, selectedItem = selectedItem,
                onItemSelected = {
                    selectedItem = it
                })
            ReDropdownMenuSelect(text = "Pilih tipe pakaian yang kamu jual",items = items, selectedItem = selectedItem,
                onItemSelected = {
                    selectedItem = it
                })

        }
    }
}

@Preview
@Composable
fun ClothesIdentityPreview() {
    ReClothesTheme {
        ClothesIdentity()
    }
}