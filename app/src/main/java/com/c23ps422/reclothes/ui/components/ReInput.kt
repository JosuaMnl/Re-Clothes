package com.c23ps422.reclothes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.R

@Composable
fun ReInput(input: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType, modifier: Modifier = Modifier) {
    Column(modifier.padding(16.dp)) {
        Text(text = stringResource(R.string.placeholder_label), style = MaterialTheme.typography.h5)
        OutlinedTextField(value = input, label = { Text(stringResource(R.string.placeholder_label))} , onValueChange = onValueChange, keyboardOptions = KeyboardOptions(keyboardType = keyboardType))
    }
}

@Preview
@Composable
fun ReInputPreview(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    ReInput(input = input , keyboardType = KeyboardType.Text, onValueChange = { newInput ->
        input = newInput
    } )
}