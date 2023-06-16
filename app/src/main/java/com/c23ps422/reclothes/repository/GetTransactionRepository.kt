package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.GetTransactionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetTransactionRepository constructor(
    private val apiService: ApiService
): Repository{

    suspend fun getTransaction(): Flow<GetTransactionResponse> {
        val data = apiService.getTransaction()
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: GetTransactionRepository? = null
        fun getInstance(
            apiService: ApiService
        ): GetTransactionRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: GetTransactionRepository(apiService)
        }.also { INSTANCE = it }
    }
}