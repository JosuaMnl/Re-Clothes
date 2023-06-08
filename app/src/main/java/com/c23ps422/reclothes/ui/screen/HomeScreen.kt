package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.ui.components.ReBottomNavigation
import com.c23ps422.reclothes.ui.components.ReCard
import com.c23ps422.reclothes.ui.components.ReSearchBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    HomeContent()
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Welcome Calvin Saputra!",
            modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        Text(
            text = "Yuk jual pakaian enggak terpakaimu!",
            modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            fontWeight = FontWeight.Bold
        )
        ReSearchBar(query = "", onQueryChange = {})

        Column(modifier.padding(16.dp)) {
            Text(
                text = "Do it Yourself",
                fontWeight = FontWeight.Bold
            )
            LazyRow(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                items(10) {
                    ReCard(R.drawable.ic_launcher_background)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Column(modifier.padding(16.dp)) {
            Text(
                text = "Achievements",
                fontWeight = FontWeight.Bold
            )
            LazyRow(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {
                items(10) {
                    ReCard(R.drawable.ic_launcher_background)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    ReClothesTheme {
        HomeScreen()
    }
}