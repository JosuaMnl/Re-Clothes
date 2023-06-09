package com.c23ps422.reclothes.data

import com.c23ps422.reclothes.model.DIY
import com.c23ps422.reclothes.model.DIYSource
import com.c23ps422.reclothes.model.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DIYRepository : Repository {
    private val diy = mutableListOf<DIY>()

    init {
        if (diy.isEmpty()) {
            DIYSource.dummyDIY.forEach {
                diy.add(it)
            }
        }
    }

    fun getAllDIY(): Flow<List<DIY>> {
        return flowOf(diy)
    }

    fun getDiyById(diyId: Int): DIY {
        return diy.first {
            it.id == diyId
        }
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