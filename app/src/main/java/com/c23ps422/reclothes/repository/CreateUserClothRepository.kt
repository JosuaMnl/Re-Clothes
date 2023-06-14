package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.CreateUserClothResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CreateUserClothRepository constructor(
    private val apiService: ApiService
) : Repository {

    suspend fun createUserCloth(
        user_id: String,
        amount_of_clothes: String
    ): Flow<CreateUserClothResponse> {
        val data = apiService.createUserCloth(user_id, amount_of_clothes)
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: CreateUserClothRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CreateUserClothRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: CreateUserClothRepository(apiService)
        }.also { INSTANCE = it }
    }
}