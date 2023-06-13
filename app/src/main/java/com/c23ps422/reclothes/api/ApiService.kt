package com.c23ps422.reclothes.api

import LoginRegisterResponse
import com.c23ps422.reclothes.model.response.CreateUserClothResponse
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
        @Field("role") roles: String = "1",
    ): LoginRegisterResponse

    @FormUrlEncoded
    @POST("userCloth/create")
    suspend fun createUserCloth(
        @Field("user_id") user_id: String,
        @Field("amount_of_clothes") amount_of_clothes: String
    ): CreateUserClothResponse
}