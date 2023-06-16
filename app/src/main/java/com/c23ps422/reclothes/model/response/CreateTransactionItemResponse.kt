package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class CreateTransactionItemResponse(
    @SerializedName("meta")
    val meta: TransactionItemMeta,
    @SerializedName("data")
    val data: TransactionItemData
)

data class TransactionItemMeta(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
)

data class TransactionItemData(
    @SerializedName("TransactionItem")
    val transactionItem: TransactionItem
)

data class TransactionItem(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("transaction_id")
    val transactionId: String,
    @SerializedName("cloth_id")
    val clothId: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)
