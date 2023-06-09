package com.c23ps422.reclothes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.DIYRepository
import com.c23ps422.reclothes.model.DIY
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
}