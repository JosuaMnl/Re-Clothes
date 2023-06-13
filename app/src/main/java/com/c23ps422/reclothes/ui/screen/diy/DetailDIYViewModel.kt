package com.c23ps422.reclothes.ui.screen.diy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.repository.DIYRepository
import com.c23ps422.reclothes.model.diy.DIY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailDIYViewModel(private val repository: DIYRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DIY>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DIY>>
        get() = _uiState

    fun getDiyById(diyId: Int){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getDiyById(diyId))
        }
    }

    // Companion object that contains a factory method for creating a ViewModelProvider.Factory for DetailDIYViewModel
    companion object {
        /**
         * Factory method to create a ViewModelProvider.Factory for DetailDIYViewModel
         * @param diyRepository The DIYRepository instance to be passed to DetailDIYViewModel
         * @return ViewModelProvider.Factory instance
         */
        fun provideFactory(
            diyRepository: DIYRepository = Injection.provideDIYRepository()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            /**
             * Creates a new instance of DetailDIYViewModel with the provided DIYRepository
             * @param modelClass The class of the ViewModel to be created
             * @return The created DetailDIYViewModel instance
             */
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailDIYViewModel(diyRepository) as T
            }
        }
    }
}