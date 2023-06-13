package com.c23ps422.reclothes.ui.screen.medals

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.model.medals.Medals
import com.c23ps422.reclothes.ui.components.ReMedal
import com.c23ps422.reclothes.common.ViewModelFactory

@Composable
fun MedalsScreen(
    modifier: Modifier = Modifier,
    viewModel: MedalsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance(Injection.provideMedalsRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getAllMedals()
            }

            is UiState.Success -> {
                MedalsContent(medals = uiState.data)
            }

            is UiState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun MedalsContent(modifier: Modifier = Modifier, medals: List<Medals>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = "Medals",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
        items(medals) { medal ->
            ReMedal(
                medalName = medal.title,
                medalDescription = medal.description,
                photoUrl = medal.photoUrl,
                xp = medal.xp,
                progressValue = medal.progress
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MedalsScreenPreview() {
    MedalsScreen()
}