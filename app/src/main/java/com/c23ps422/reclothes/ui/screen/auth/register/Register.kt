package com.c23ps422.reclothes.ui.screen.auth.register

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.pref.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.components.ReTextFieldWithIcon
import com.c23ps422.reclothes.ui.navigation.Screen
import com.c23ps422.reclothes.ui.screen.auth.login.dataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Register(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = remember { context.dataStore }
    val pref = ReClothesPreference.getInstance(dataStore)
    val viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModel.provideFactory(pref, Application())
    )

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier.padding(16.dp),
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = modifier.padding(it)) {
            item {
                Spacer(modifier = Modifier.height(64.dp))
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.rg_photo_desc),
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(R.string.register_message),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))
                ReTextFieldWithIcon(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = stringResource(R.string.rg_username),
                    painterResource = painterResource(
                        id = R.drawable.icon_username
                    ),
                    trailingIcon = {}
                )
                Spacer(modifier = Modifier.height(4.dp))
                ReTextFieldWithIcon(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = stringResource(R.string.rg_email),
                    painterResource = painterResource(
                        id = R.drawable.icon_email
                    ),
                    trailingIcon = {}
                )
                Spacer(modifier = Modifier.height(4.dp))
                ReTextFieldWithIcon(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = stringResource(R.string.rg_password),
                    painterResource = painterResource(
                        id = R.drawable.icon_password
                    ),
                    trailingIcon = {
                        val iconImage = if (passwordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }

                        var description = if (passwordVisible) {
                            stringResource(R.string.rg_hd_pwd)
                        } else {
                            stringResource(R.string.rg_sw_pwd)
                        }

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(iconImage, description)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(4.dp))
                ReTextFieldWithIcon(
                    value = passwordConfirmation,
                    onValueChange = {
                        passwordConfirmation = it
                    },
                    label = stringResource(R.string.rg_password_confirmation),
                    painterResource = painterResource(
                        id = R.drawable.icon_password_confirmation
                    ),
                    isError = password != passwordConfirmation,
                    trailingIcon = {
                        val iconImage = if (passwordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }

                        var description = if (passwordVisible) {
                            stringResource(R.string.rg_hd_pwd)
                        } else {
                            stringResource(R.string.rg_sw_pwd)
                        }

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(iconImage, description)
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                Spacer(modifier = Modifier.height(12.dp))
                ReButtonFullRounded(text = stringResource(R.string.welcome_register),
                    onClick = {
                        viewModel.postRegister(username, email, password, passwordConfirmation)
                    })
            }
        }
    }

    val scope = rememberCoroutineScope()
    viewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(uiState.data.message)
                    delay(200)
                    navController.navigate(Screen.Home.route)
                }
            }

            is UiState.Error -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(uiState.errorMessage)
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegisterPreview() {
    val navController = rememberNavController()
    Register(navController)
}