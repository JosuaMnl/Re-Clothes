package com.c23ps422.reclothes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.MedalsRepository
import com.c23ps422.reclothes.model.Medals
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
}