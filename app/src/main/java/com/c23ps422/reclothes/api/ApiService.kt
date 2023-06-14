package com.c23ps422.reclothes.api

import LoginRegisterResponse
import com.c23ps422.reclothes.model.response.CreateUserClothResponse
import com.c23ps422.reclothes.model.response.UserProfileResponse
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
}