package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReBottomSheet(
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Bottom sheet", fontSize = 60.sp)
            }
        },
        sheetBackgroundColor = Color.Green,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isVisible) {
                        sheetState.hide()
                    } else {
                        sheetState.show()
                    }
                }
            }) {
                Text(text = "Toggle Sheet")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ReBottomSheetPreview() {
    ReClothesTheme {
        ReBottomSheet()
    }
}