package com.c23ps422.reclothes.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.response.UserData
import com.c23ps422.reclothes.model.response.UserMeta
import com.c23ps422.reclothes.model.response.UserProfileResponse
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.components.ReTextField
import com.c23ps422.reclothes.ui.theme.ReClothesTheme
import com.c23ps422.reclothes.R

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    userInfo: UserProfileResponse
) {
    val data = userInfo.data
    var name by rememberSaveable { mutableStateOf(data.name) }
    var email by rememberSaveable { mutableStateOf(data.email) }
    var accountNumber by rememberSaveable { mutableStateOf(data.accountNumber) }
    var address by rememberSaveable { mutableStateOf(data.address) }
    var phoneNumber by rememberSaveable { mutableStateOf(data.phoneNumber) }
    var accountType by rememberSaveable { mutableStateOf(data.accountType) }

    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.provideFactory(context)
    )

    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(modifier = modifier) {
            item {
                Text(
                    text = stringResource(R.string.pc_title),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                ReTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = stringResource(R.string.pc_name)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = stringResource(R.string.pc_email)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = accountNumber ?: stringResource(R.string.pc_account_number_empty),
                    onValueChange = {
                        accountNumber = it
                    },
                    label = stringResource(R.string.pc_account_number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = address ?: stringResource(R.string.pc_address_empty),
                    onValueChange = {
                        address = it
                    },
                    label = stringResource(R.string.pc_address)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = phoneNumber ?: stringResource(R.string.pc_phone_number_empty),
                    onValueChange = {
                        phoneNumber = it
                    },
                    label = stringResource(R.string.pc_phone_number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = accountType ?: stringResource(R.string.pc_account_type_empty),
                    onValueChange = {
                        accountType = it
                    },
                    label = stringResource(R.string.pc_account_type)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ReButtonFullRounded(text = stringResource(R.string.pc_update), onClick = {
                    userViewModel.updateUser(
                        name = name,
                        email = email,
                        account_number = accountNumber ?: "",
                        address = address ?: "",
                        phone_number = phoneNumber ?: "",
                        account_type = accountType ?: ""
                    )
                })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                    shape = RoundedCornerShape(50.dp),
                    onClick = {

                    })
                {
                    Text(stringResource(R.string.pc_logout))
                }
            }
        }
    }

    userViewModel.uiState2.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Idle -> {}

            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                Toast.makeText(
                    context,
                    stringResource(R.string.pc_update_success),
                    Toast.LENGTH_SHORT
                ).show()
            }

            is UiState.Error -> {
                Toast.makeText(
                    context,
                    stringResource(R.string.pc_update_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    val userProfile = UserProfileResponse(
        data = UserData(
            name = "Test User",
            email = "testuser@gmail.com",
            phoneNumber = "123-456-7890",
            address = "123, Test Street, Test City, Test State",
            accountNumber = "1234567890",
            createdAt = "Now",
            emailVerifiedAt = "",
            id = "",
            updatedAt = "",
            accountType = "",
            roles = ""
        ),
        meta = UserMeta(
            code = 200,
            message = "Yoi",
            status = "Success"
        )
    )

    ReClothesTheme {
        ProfileContent(
            userInfo = userProfile
        )
    }
}
