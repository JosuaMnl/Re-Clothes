package com.c23ps422.reclothes.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.c23ps422.reclothes.R
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.components.ReTextField
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Composable
fun Login(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    val dataStore: DataStore<Preferences> = remember { context.dataStore }

    val pref = ReClothesPreference.getInstance(dataStore)

    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModel.provideFactory(pref)
    )


    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState
    ) {
        LazyColumn(modifier = modifier.padding(horizontal = 14.dp)) {
            item {
                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.height(64.dp))
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "Change this later on",
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
//                    Toast.makeText(context, "${email} ${password}", Toast.LENGTH_SHORT).show()
                        viewModel.postLogin(email, password)
                    })
            }
        }
    }

    val scope = rememberCoroutineScope()

    viewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {

            }

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(uiState.data.message)
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
    Login()
}