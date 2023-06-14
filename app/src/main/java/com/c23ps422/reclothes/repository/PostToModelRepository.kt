package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.UploadClothResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PostToModelRepository constructor(
    private val apiService: ApiService
): Repository {

    suspend fun createClothImage(
        original_image: MultipartBody.Part,
        user_cloth_id: RequestBody
    ): Flow<UploadClothResponse> {
        val data = apiService.createClothImage(original_image, user_cloth_id)
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: PostToModelRepository? = null
        fun getInstance(
            apiService: ApiService
        ): PostToModelRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: PostToModelRepository(apiService)
        }.also { INSTANCE = it }
    }
}