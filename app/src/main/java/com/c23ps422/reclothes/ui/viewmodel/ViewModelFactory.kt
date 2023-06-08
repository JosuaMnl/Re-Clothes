package com.c23ps422.reclothes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c23ps422.reclothes.data.DIYRepository
import com.c23ps422.reclothes.data.MedalsRepository
import com.c23ps422.reclothes.model.Repository

class ViewModelFactory(
    private val repository: Repository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedalsViewModel::class.java)) {
            return MedalsViewModel(repository as MedalsRepository) as T
        }
        if (modelClass.isAssignableFrom(DIYViewModel::class.java)) {
            return DIYViewModel(repository as DIYRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}