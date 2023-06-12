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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DIYViewModel(private val repository: DIYRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<DIY>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<DIY>>>
        get() = _uiState

    fun getAllDIY() {
        viewModelScope.launch {
            repository.getAllDIY().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { diy ->
                _uiState.value = UiState.Success(diy)
            }
        }
    }

    // Companion object that contains a factory method for creating a ViewModelProvider.Factory for DIYViewModel
    companion object {
        /**
         * Factory method to create a ViewModelProvider.Factory for DIYViewModel
         * @param diyRepository The DIYRepository instance to be passed to DIYViewModel
         * @return ViewModelProvider.Factory instance
         */
        fun provideFactory(
            diyRepository: DIYRepository = Injection.provideDIYRepository()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            /**
             * Creates a new instance of DIYViewModel with the provided DIYRepository
             * @param modelClass The class of the ViewModel to be created
             * @return The created DIYViewModel instance
             */
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DIYViewModel(diyRepository) as T
            }
        }
    }

}