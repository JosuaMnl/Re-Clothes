package com.c23ps422.reclothes.di

import android.content.Context
import com.c23ps422.reclothes.api.ApiConfig
import com.c23ps422.reclothes.repository.CreateUserClothRepository
import com.c23ps422.reclothes.repository.DIYRepository
import com.c23ps422.reclothes.repository.MedalsRepository
import com.c23ps422.reclothes.repository.PostToModelRepository
import com.c23ps422.reclothes.repository.UserRepository

object Injection {
    fun provideMedalsRepository(): MedalsRepository {
        return MedalsRepository()
    }

    fun provideDIYRepository(): DIYRepository {
        return DIYRepository()
    }

    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService(context)
        return UserRepository.getInstance(apiService)
    }

    fun provideCreateUserClothRepository(context: Context): CreateUserClothRepository {
        val apiService = ApiConfig.getApiService(context)
        return CreateUserClothRepository.getInstance(apiService)
    }

    fun providePostToModelRepository(context: Context): PostToModelRepository {
        val apiService = ApiConfig.getApiService(context)
        return PostToModelRepository.getInstance(apiService)
    }
}