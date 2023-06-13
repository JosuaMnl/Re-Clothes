package com.c23ps422.reclothes.di

import com.c23ps422.reclothes.repository.DIYRepository
import com.c23ps422.reclothes.repository.MedalsRepository

object Injection {
    fun provideMedalsRepository(): MedalsRepository {
        return MedalsRepository()
    }

    fun provideDIYRepository(): DIYRepository {
        return DIYRepository()
    }
}