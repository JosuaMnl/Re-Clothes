package com.c23ps422.reclothes.data

import com.c23ps422.reclothes.model.Medals
import com.c23ps422.reclothes.model.MedalsSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MedalsRepository {
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