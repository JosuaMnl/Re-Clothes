package com.c23ps422.reclothes.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.model.DIY
import com.c23ps422.reclothes.model.Medals
import com.c23ps422.reclothes.ui.components.ReCard
import com.c23ps422.reclothes.ui.components.ReSearchBar
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import com.c23ps422.reclothes.ui.viewmodel.DIYViewModel
import com.c23ps422.reclothes.ui.viewmodel.MedalsViewModel
import com.c23ps422.reclothes.ui.viewmodel.ViewModelFactory

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    medalsViewModel: MedalsViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideMedalsRepository())
    ),
    diyViewModel: DIYViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideDIYRepository())
    )
) {
    val diyState by diyViewModel.uiState.collectAsState(initial = UiState.Loading)
    val medalsState by medalsViewModel.uiState.collectAsState(initial = UiState.Loading)

    when {
        diyState is UiState.Loading && medalsState is UiState.Loading -> {
            diyViewModel.getAllDIY()
            medalsViewModel.getAllMedals()
        }

        diyState is UiState.Success && medalsState is UiState.Success -> {
            val diyData = (diyState as UiState.Success).data
            val medalsData = (medalsState as UiState.Success).data
            HomeContent(diy = diyData, medals = medalsData)
        }
        diyState is UiState.Error || medalsState is UiState.Error -> {

        }
    }
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, diy: List<DIY>, medals: List<Medals>) {
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
                items(diy) { item ->
                    ReCard(item.photoUrl, item.title)
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
                items(medals) { item ->
                    ReCard(item.photoUrl, item.title)
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