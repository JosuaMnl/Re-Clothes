package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class CreateClothResponse(

    @field:SerializedName("data")
    val data: ClothData,

    @field:SerializedName("meta")
    val meta: ClothMeta
)

data class ClothData(

    @field:SerializedName("cloth")
    val cloth: Cloth
)

data class Cloth(

    @field:SerializedName("cloth_image_id")
    val clothImageId: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("is_ready")
    val isReady: Boolean,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("created_at")
    val createdAt: String
)

data class ClothMeta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)
