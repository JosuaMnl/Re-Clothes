package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class CreateUserClothResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("meta")
	val meta: Meta
)

data class UserClothes(

	@field:SerializedName("amount_of_clothes")
	val amountOfClothes: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: String
)

data class Meta(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("UserClothes")
	val userClothes: UserClothes
)
