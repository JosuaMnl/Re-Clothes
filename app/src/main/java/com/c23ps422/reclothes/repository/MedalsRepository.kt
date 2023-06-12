package com.c23ps422.reclothes.repository

import com.c23ps422.reclothes.model.medals.Medals
import com.c23ps422.reclothes.model.medals.MedalsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MedalsRepository: Repository {
    private val medals = mutableListOf<Medals>()

    init {
        if (medals.isEmpty()) {
            MedalsSource.dummyMedals.forEach {
                medals.add(it)
            }
        }
    }

    fun getAllMedals(): Flow<List<Medals>> {
        return flowOf(medals)
    }

    companion object {
        @Volatile
        private var instance: MedalsRepository? = null

        fun getInstance(): MedalsRepository =
            instance ?: synchronized(this) {
                MedalsRepository().apply {
                    instance = this
                }
            }
    }
}