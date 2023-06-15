package com.c23ps422.reclothes.model.response

import com.google.gson.annotations.SerializedName

data class UploadClothResponse(

    @field:SerializedName("data")
    val data: CreateImageData,

    @field:SerializedName("meta")
    val meta: ClothImageMeta
)

data class ClothImageMeta(

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)

data class CreateImageData(

    @field:SerializedName("clothImage")
    val clothImage: ClothImage,

    @field:SerializedName("originalImageUrl")
    val originalImageUrl: String,

    @field:SerializedName("defectsImageUrl")
    val defectsImageUrl: String
)

data class ClothImage(

    @field:SerializedName("user_cloth_id")
    val userClothId: String,

    @field:SerializedName("original_image")
    val originalImage: String,

    @field:SerializedName("defects_proof")
    val defectsProof: String,

    @field:SerializedName("fabric_status")
    val fabricStatus: Int,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("created_at")
    val createdAt: String
)
