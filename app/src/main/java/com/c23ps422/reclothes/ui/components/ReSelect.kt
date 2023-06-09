package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

@Composable
fun ReSelect(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val list = listOf("Gucci", "Pull N Bear", "Nihao", "Roughneck")
    var selectedItem by remember { mutableStateOf("") }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Column(modifier = modifier.padding(20.dp)) {
        OutlinedTextField(
            value = selectedItem,
            readOnly = true,
            onValueChange = { selectedItem = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                }
                .clickable {
                    expanded = !expanded
                },
            label = {
                Text(text = "Select Item")
            },
            trailingIcon = {
                Icon(imageVector = icon, contentDescription = "", modifier = Modifier.clickable {
                    expanded = !expanded
                })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        )
        {
            list.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedItem = item
                    expanded = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReSelect2(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val list = listOf("Gucci", "Pull N Bear", "Nihao", "Roughneck")
    var selectedItem by remember { mutableStateOf("") }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.clickable {
                expanded = !expanded
            },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(icon, contentDescription = "Dropdown Icon")
                }
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            list.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedItem = item
                    expanded = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReSelectPreview() {
    ReClothesTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReSelect()
            Spacer(modifier = Modifier.height(16.dp))
            ReSelect2()
        }
    }
}