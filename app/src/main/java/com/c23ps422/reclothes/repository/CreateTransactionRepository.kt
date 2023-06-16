package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.CreateTransactionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CreateTransactionRepository constructor(
    private val apiService: ApiService
) : Repository {

    suspend fun createTransaction(): Flow<CreateTransactionResponse> {
        val data = apiService.createTransaction()
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: CreateTransactionRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CreateTransactionRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: CreateTransactionRepository(apiService)
        }.also { INSTANCE = it }
    }
}