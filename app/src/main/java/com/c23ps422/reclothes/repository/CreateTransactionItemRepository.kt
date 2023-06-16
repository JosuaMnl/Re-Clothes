package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.model.response.CreateTransactionItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CreateTransactionItemRepository constructor(
    private val apiService: ApiService
): Repository {

    suspend fun createTransactionItem(
        transaction_id: String,
        cloth_id: String
    ): Flow<CreateTransactionItemResponse> {
        val data = apiService.createItemTransaction(transaction_id, cloth_id)
        return flowOf(data)
    }

    companion object {
        @Volatile
        private var INSTANCE: CreateTransactionItemRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CreateTransactionItemRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: CreateTransactionItemRepository(apiService)
        }.also { INSTANCE = it }
    }
}