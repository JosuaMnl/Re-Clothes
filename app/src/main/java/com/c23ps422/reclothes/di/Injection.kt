package com.c23ps422.reclothes.di

import com.c23ps422.reclothes.data.MedalsRepository

object Injection {
    fun provideMedalsRepository(): MedalsRepository{
        return MedalsRepository()
    }
}