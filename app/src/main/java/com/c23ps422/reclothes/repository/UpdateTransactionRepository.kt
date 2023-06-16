package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.UpdateTransactionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UpdateTransactionRepository constructor(
    private val apiService: ApiService
): Repository {

    suspend fun updateTransaction(
        transaction_id: String,
        weight: String,
        quantity: String,
        address: String,
        status: String
    ): Flow<UpdateTransactionResponse> {
        val data = apiService.updateTransaction(transaction_id, weight, quantity, address, status)
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: UpdateTransactionRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UpdateTransactionRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: UpdateTransactionRepository(apiService)
        }.also { INSTANCE = it }
    }
}