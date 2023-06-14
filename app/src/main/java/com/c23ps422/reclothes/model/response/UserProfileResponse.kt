package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(

	@field:SerializedName("data")
	val data: UserData,

	@field:SerializedName("meta")
	val meta: UserMeta
)

data class UserData(

	@field:SerializedName("account_number")
	val accountNumber: String ?= null,

	@field:SerializedName("account_type")
	val accountType: String ?= null,

	@field:SerializedName("address")
	val address: String ?= null,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("roles")
	val roles: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any ?= null,

	@field:SerializedName("phone_number")
	val phoneNumber: String ?= null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String
)

data class UserMeta(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
