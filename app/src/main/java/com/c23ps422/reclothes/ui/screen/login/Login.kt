package com.c23ps422.reclothes.ui.screen.login

import android.app.Application
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.components.ReTextField
import com.c23ps422.reclothes.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A custom extension property on the Context class that initializes and provides a handle to the Preferences DataStore.
 * This DataStore is used to persist simple key-value pairs and is named 'settings'.
 * The 'preferencesDataStore' delegate function provided by Jetpack DataStore is used to create the DataStore instance.
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * A Jetpack Compose @Composable function which is responsible for creating the Login screen UI.
 * It makes use of the LoginViewModel for business logic.
 *
 * @property navController a NavController, which is used to manage app navigation.
 * @property modifier an optional Modifier that can be applied to the Scaffold for styling.
 *
 * This composable creates a login form with two fields, 'Email' and 'Password', and a 'Log In' button.
 * The fields use mutable states for keeping track of their values.
 * An instance of ReClothesPreference is created to handle user preferences.
 * The LoginViewModel is initialized with the instance of ReClothesPreference and the application context.
 *
 * The Composable observes the uiState in the ViewModel, and when it receives a value,
 * it reacts accordingly:
 *   - If the state is Loading, a progress indicator is displayed.
 *   - If the state is Success, a Snackbar is shown with the success message, and after a delay of 200 milliseconds, it navigates to the Home screen.
 *   - If the state is Error, a Snackbar is shown with the error message.
 *
 */
@Composable
fun Login(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = remember { context.dataStore }
    val pref = ReClothesPreference.getInstance(dataStore)
    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.provideFactory(pref, Application())
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = modifier.padding(horizontal = 14.dp)) {
            item {
                Spacer(modifier = Modifier.height(64.dp))
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "ReClothes",
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(R.string.login_message),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(48.dp))
                ReTextField(
                    value = email,
                    onValueChange = { txt ->
                        email = txt
                    },
                    label = "Email"
                )
                Spacer(modifier = Modifier.height(12.dp))
                ReTextField(
                    value = password,
                    onValueChange = { txt ->
                        password = txt
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    label = "Password",

                    )
                Spacer(modifier = Modifier.height(12.dp))
                ReButtonFullRounded(
                    text = stringResource(R.string.welcome_login), onClick = {
                        viewModel.postLogin(email, password)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    Login(navController)
}