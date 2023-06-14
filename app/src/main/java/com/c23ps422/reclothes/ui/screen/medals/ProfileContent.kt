package com.c23ps422.reclothes.ui.screen.medals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.c23ps422.reclothes.model.response.UserData
import com.c23ps422.reclothes.model.response.UserMeta
import com.c23ps422.reclothes.model.response.UserProfileResponse
import com.c23ps422.reclothes.ui.components.ReButtonFullRounded
import com.c23ps422.reclothes.ui.components.ReTextField
import com.c23ps422.reclothes.ui.theme.ReClothesTheme

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


    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        LazyColumn(modifier = modifier) {
            item {
                Text(text = "Your Profile", style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold
                ))
                Spacer(modifier = Modifier.height(16.dp))
                ReTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = "Nama Lengkap"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = "Alamat Email"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = accountNumber ?: "Nomor Rekening kamu masih kosong",
                    onValueChange = {
                        accountNumber = it
                    },
                    label = "Nomor Rekening"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = address  ?: "Alamat kamu masih kosong",
                    onValueChange = {
                        address = it
                    },
                    label = "Alamat"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = phoneNumber  ?: "Nomor Telepon kamu masih kosong",
                    onValueChange = {
                        phoneNumber = it
                    },
                    label = "Nomor Telepon"
                )
                Spacer(modifier = Modifier.height(8.dp))
                ReTextField(
                    value = accountType  ?: "Tipe Rekening kamu masih kosong",
                    onValueChange = {
                        accountType = it
                    },
                    label = "Tipe Rekening"
                )
                Spacer(modifier = Modifier.height(16.dp))
                ReButtonFullRounded(text = "Update Profile", onClick = {

                })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                    shape = RoundedCornerShape(50.dp),
                    onClick = {})
                {
                    Text(text = "Logout")
                }
//                Text(
//                    text = userInfo.data.name,
//                    style = MaterialTheme.typography.h5,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .padding(top = 16.dp)
//                        .fillMaxWidth()
//                )
//
//                Text(
//                    text = userInfo.data.email,
//                    style = MaterialTheme.typography.body1,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .fillMaxWidth()
//                )
//
//                Text(
//                    text = userInfo.data.phoneNumber ?: "(Nomor HP kamu masih kosong)",
//                    style = MaterialTheme.typography.body1,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .fillMaxWidth()
//                )
//
//                Text(
//                    text = userInfo.data.address ?: "(Alamat kamu masih kosong)",
//                    style = MaterialTheme.typography.body1,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .fillMaxWidth()
//                )
//
//                Text(
//                    text = userInfo.data.accountNumber ?: "(No Rekening kamu masih kosong)",
//                    style = MaterialTheme.typography.body1,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .padding(top = 8.dp)
//                        .fillMaxWidth()
//                )
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
