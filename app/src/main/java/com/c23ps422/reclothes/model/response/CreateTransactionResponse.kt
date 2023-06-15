package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class CreateTransactionResponse(

    @field:SerializedName("data")
    val data: TransactionData,

    @field:SerializedName("meta")
    val meta: TransactionMeta
)

data class TransactionData(

    @field:SerializedName("Transaction")
    val transaction: Transaction
)

data class Transaction(

    @field:SerializedName("user_id")
    val userId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("created_at")
    val createdAt: String
)

data class TransactionMeta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
