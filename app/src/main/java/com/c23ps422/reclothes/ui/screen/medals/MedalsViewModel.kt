package com.c23ps422.reclothes.ui.screen.medals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.repository.MedalsRepository
import com.c23ps422.reclothes.model.medals.Medals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MedalsViewModel(private val repository: MedalsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Medals>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Medals>>>
        get() = _uiState

    fun getAllMedals() {
        viewModelScope.launch {
            repository.getAllMedals().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { medals ->
                _uiState.value = UiState.Success(medals)
            }
        }
    }

    // Companion object that contains a factory method for creating a ViewModelProvider.Factory for MedalsViewModel
    companion object {
        /**
         * Factory method to create a ViewModelProvider.Factory for MedalsViewModel
         * @param medalsRepository The MedalsRepository instance to be passed to MedalsViewModel
         * @return ViewModelProvider.Factory instance
         */
        fun provideFactory(
            medalsRepository: MedalsRepository = Injection.provideMedalsRepository()
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            /**
             * Creates a new instance of MedalsViewModel with the provided MedalsRepository
             * @param modelClass The class of the ViewModel to be created
             * @return The created MedalsViewModel instance
             */
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MedalsViewModel(medalsRepository) as T
            }
        }
    }
}