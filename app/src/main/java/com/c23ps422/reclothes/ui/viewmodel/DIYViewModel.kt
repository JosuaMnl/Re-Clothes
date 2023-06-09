package com.c23ps422.reclothes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.DIYRepository
import com.c23ps422.reclothes.model.DIY
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
}