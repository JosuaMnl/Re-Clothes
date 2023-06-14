package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.model.response.UserProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository constructor(
    private val apiService: ApiService
) : Repository {
    fun getUser(): Flow<UiState<UserProfileResponse>> = flow {
        emit(UiState.Loading)

        try {
            val userProfileResponse = apiService.getUser()
            emit(UiState.Success(userProfileResponse))
        } catch (e: Exception) {
            emit(UiState.Error("An error has occured: ${e.message}"))
        }
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