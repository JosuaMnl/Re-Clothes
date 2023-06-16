package com.c23ps422.reclothes.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.diy.DIY
import com.c23ps422.reclothes.model.medals.Medals
import com.c23ps422.reclothes.pref.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReCard
import com.c23ps422.reclothes.ui.components.ReMisc
import com.c23ps422.reclothes.ui.components.ReSearchBar
import com.c23ps422.reclothes.ui.screen.diy.DIYViewModel
import com.c23ps422.reclothes.ui.screen.medals.MedalsViewModel
import com.c23ps422.reclothes.ui.screen.profile.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    pref: ReClothesPreference,
    userViewModel: UserViewModel,
    medalsViewModel: MedalsViewModel = viewModel(
        factory = MedalsViewModel.provideFactory()
    ),
    diyViewModel: DIYViewModel = viewModel(
        factory = DIYViewModel.provideFactory()
    ),
    navigateToDetail: (Int) -> Unit
) {
    var username by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        val token = pref.getToken().firstOrNull()
        if (token != null && userViewModel.username.value.isEmpty()) {
            userViewModel.getUser(token)
        }
    }

    userViewModel.username.collectAsState().value.let {
        username = it
    }
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
            HomeContent(
                diy = diyData,
                medals = medalsData,
                navigateToDetail = navigateToDetail,
                username = username
            )
        }

        diyState is UiState.Error || medalsState is UiState.Error -> {

        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    diy: List<DIY>,
    medals: List<Medals>,
    username: String,
    navigateToDetail: (Int) -> Unit
) {
    // Tsukifell: changed the Column into LazyColumn, makes it scrollable in another devices
    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.hc_name) + " $username",
                modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )
            Text(
                text = stringResource(R.string.hc_message),
                modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ReMisc(
                        name = stringResource(R.string.hc_money_title),
                        description = stringResource(R.string.hc_money_desc),
                        icon = Icons.Default.Payment
                    )
                    ReMisc(
                        name = stringResource(R.string.hc_exp_title),
                        description = stringResource(R.string.hc_exp_desc),
                        icon = Icons.Default.TrendingUp
                    )
                }
            }

            ReSearchBar(query = "", onQueryChange = {})
        }

        item {
            Column(modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = stringResource(R.string.hc_diy),
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    items(diy) { item ->
                        ReCard(
                            item.photoUrl,
                            item.title,
                            item.viewsCount,
                            modifier = Modifier.clickable {
                                navigateToDetail(item.id)
                            })
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }

        item {
            Column(modifier.padding(top = 4.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)) {
                Text(
                    text = stringResource(R.string.hc_achievement),
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    items(medals) { item ->
                        ReCard(item.photoUrl, item.title, item.xp)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun HomeScreenPreview() {
//    ReClothesTheme {
//        HomeScreen()
//    }
//}