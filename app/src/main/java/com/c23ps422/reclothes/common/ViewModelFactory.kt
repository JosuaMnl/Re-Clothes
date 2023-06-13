package com.c23ps422.reclothes.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps422.reclothes.repository.DIYRepository
import com.c23ps422.reclothes.repository.MedalsRepository
import com.c23ps422.reclothes.repository.Repository
import com.c23ps422.reclothes.ui.screen.diy.DIYViewModel
import com.c23ps422.reclothes.ui.screen.diy.DetailDIYViewModel
import com.c23ps422.reclothes.ui.screen.login.LoginViewModel
import com.c23ps422.reclothes.ui.screen.medals.MedalsViewModel

class ViewModelFactory(
    private val repository: Repository,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedalsViewModel::class.java)) {
            return MedalsViewModel(repository as MedalsRepository) as T
        } else if (modelClass.isAssignableFrom(DIYViewModel::class.java)) {
            return DIYViewModel(repository as DIYRepository) as T
        } else if (modelClass.isAssignableFrom(DetailDIYViewModel::class.java)) {
            return DetailDIYViewModel(repository as DIYRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory? = null

        fun getInstance(repository: Repository): ViewModelFactory {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: ViewModelFactory(repository).also {
                    INSTANCE = it
                }
            }
        }
    }
}