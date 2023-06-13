package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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

@Composable
fun ReDropdownMenuSelect(
    text: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Text(text = text)
    Box(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) { // Use fillMaxSize() modifier
        Text(
            text = selectedItem,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(12.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                IconButton(
                    onClick = { expanded = false },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            items.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    expanded = false
                }) {
                    Text(text = item)
                }
                if (index < items.size - 1) {
                    Divider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReSelectPreview() {
    val items = listOf("Option 1", "Option 2", "Option 3")
    var selectedItem by remember { mutableStateOf("Option 1") }

    ReClothesTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReSelect()
            Spacer(modifier = Modifier.height(16.dp))
            ReSelect2()
            ReDropdownMenuSelect(
                text = "Pilih merek pakaian yang kamu jual",
                items = items,
                selectedItem = selectedItem,
                onItemSelected = { item ->
                    selectedItem = item
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}