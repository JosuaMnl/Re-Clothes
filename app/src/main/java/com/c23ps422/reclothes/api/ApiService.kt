package com.c23ps422.reclothes.api

import LoginRegisterResponse
import com.c23ps422.reclothes.model.response.CreateClothResponse
import com.c23ps422.reclothes.model.response.CreateTransactionItemResponse
import com.c23ps422.reclothes.model.response.CreateTransactionResponse
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

    @FormUrlEncoded
    @PUT("user/update")
    suspend fun updateUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("account_number") account_number: String,
        @Field("address") address: String,
        @Field("phone_number") phone_number: String,
        @Field("account_type") account_type: String
    ): UserProfileResponse

    @FormUrlEncoded
    @POST("cloth/create")
    suspend fun createCloth(
        @Field("cloth_image_id") clothImageId: String,
        @Field("type") type: String,
        @Field("description") description: String
    ): CreateClothResponse

    @POST("transaction/create")
    suspend fun createTransaction(): CreateTransactionResponse

    @FormUrlEncoded
    @POST("transaction/item/create")
    suspend fun createItemTransaction(
        @Field("transaction_id") transaction_id: String,
        @Field("cloth_id") cloth_id: String
    ): CreateTransactionItemResponse
}