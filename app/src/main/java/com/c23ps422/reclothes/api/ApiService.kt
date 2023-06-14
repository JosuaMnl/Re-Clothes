package com.c23ps422.reclothes.api

import LoginRegisterResponse
import com.c23ps422.reclothes.model.response.CreateUserClothResponse
import com.c23ps422.reclothes.model.response.UploadClothResponse
import com.c23ps422.reclothes.model.response.UserProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginRegisterResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("roles") roles: String = "1",
    ): LoginRegisterResponse

    @GET("user")
    suspend fun getUser(): UserProfileResponse

    @FormUrlEncoded
    @POST("userCloth/create")
    suspend fun createUserCloth(
        @Field("user_id") user_id: String,
        @Field("amount_of_clothes") amount_of_clothes: String
    ): CreateUserClothResponse

    @Multipart
    @POST("cloth/image/create")
    suspend fun createClothImage(
        @Part original_image: MultipartBody.Part,
        @Part("user_cloth_id") user_cloth_id: RequestBody,
    ): UploadClothResponse
}