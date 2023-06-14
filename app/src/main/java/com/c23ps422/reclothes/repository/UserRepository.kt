package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRepository constructor(
    private val apiService: ApiService
) : Repository {

    suspend fun getUser(): Flow<UserProfileResponse> {
        val data = apiService.getUser()
        return flowOf(data)
    }

    suspend fun updateUser(
        name: String,
        email: String,
        account_number: String,
        address: String,
        phone_number: String,
        account_type: String
    ): Flow<UserProfileResponse> {
        val data = apiService.updateUser(
            name = name,
            email = email,
            account_number = account_number,
            address = address,
            phone_number = phone_number,
            account_type = account_type
        )
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UserRepository(apiService)
        }.also { INSTANCE = it }
    }
}