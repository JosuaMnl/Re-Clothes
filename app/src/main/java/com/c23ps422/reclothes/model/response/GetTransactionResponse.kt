package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class GetTransactionResponse(
    @field:SerializedName("data")
    val data: GetTransactionData,
    @field:SerializedName("meta")
    val meta: GetTransactionMeta
)

data class GetTransactionMeta(
    @field:SerializedName("code")
    val code: Int,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("message")
    val message: String
)

data class GetTransactionData(
    @field:SerializedName("Transaction")
    val transaction: GetTransaction
)

data class GetTransaction(
    @field:SerializedName("data")
    val data: List<GetTransactionDetail>
)

data class GetTransactionDetail(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("user_id")
    val userId: String,
    @field:SerializedName("address")
    val address: String?,
    @field:SerializedName("weight")
    val weight: Double?,
    @field:SerializedName("quantity")
    val quantity: Int?,
    @field:SerializedName("total_selling_price")
    val totalSellingPrice: Double?,
    @field:SerializedName("total_pickup_cost")
    val totalPickupCost: Double?,
    @field:SerializedName("pickup_date")
    val pickupDate: String?,
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("account_type")
    val accountType: String?,
    @field:SerializedName("account_number")
    val accountNumber: String?,
    @field:SerializedName("created_at")
    val createdAt: String,
    @field:SerializedName("updated_at")
    val updatedAt: String,
    @field:SerializedName("deleted_at")
    val deletedAt: String?
)
