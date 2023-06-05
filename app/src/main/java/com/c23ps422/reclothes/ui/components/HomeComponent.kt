package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import com.c23ps422.reclothes.R

@Composable
fun HomeComponent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "fab")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) {
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

            Column(modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                ReSearchBar(query = "", onQueryChange = {})
                Text(
                    text = "Do it Yourself!",
                    fontWeight = FontWeight.Bold
                )
            }

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                items(10) {
                    ReCard(R.drawable.ic_launcher_background)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Column(modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Text(
                    text = "Achievements!",
                    fontWeight = FontWeight.Bold
                )
            }

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
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
fun HomeComponentPreview() {
    ReClothesTheme {
        HomeComponent()
    }
}