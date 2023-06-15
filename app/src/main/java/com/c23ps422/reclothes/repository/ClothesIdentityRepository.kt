package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.CreateClothResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ClothesIdentityRepository constructor(
    private val apiService: ApiService
): Repository {

    suspend fun createCloth(
        cloth_image_id: String,
        type: String,
        description: String
    ): Flow<CreateClothResponse> {
        val data = apiService.createCloth(cloth_image_id, type, description)
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: ClothesIdentityRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ClothesIdentityRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ClothesIdentityRepository(apiService)
        }.also { INSTANCE = it }
    }
}