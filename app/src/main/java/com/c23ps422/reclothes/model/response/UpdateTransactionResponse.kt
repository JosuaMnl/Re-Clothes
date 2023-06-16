package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class UpdateTransactionResponse(

    @field:SerializedName("data")
    val data: UpdateTransactionData,

    @field:SerializedName("meta")
    val meta: UpdateTransactionMeta
)

data class UpdateTransactionData(

    @field:SerializedName("Transaction")
    val transaction: UpdateTransactionDetails
)

data class UpdateTransactionDetails(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("user_id")
    val userId: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("weight")
    val weight: String,

    @field:SerializedName("quantity")
    val quantity: String,

    @field:SerializedName("total_selling_price")
    val totalSellingPrice: Int,

    @field:SerializedName("total_pickup_cost")
    val totalPickupCost: Any ?= null,

    @field:SerializedName("pickup_date")
    val pickupDate: Any ?= null,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("account_type")
    val accountType: String,

    @field:SerializedName("account_number")
    val accountNumber: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("deleted_at")
    val deletedAt: Any ?= null
)

data class UpdateTransactionMeta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
