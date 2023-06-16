package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.response.GetTransactionResponse
import com.c23ps422.reclothes.repository.GetTransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GetTransactionViewModel(
    private val repository: GetTransactionRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<GetTransactionResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<GetTransactionResponse>> get() = _uiState

    fun getTransaction() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.getTransaction().catch { e ->
                _uiState.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState.value = UiState.Success(data)
            }
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GetTransactionViewModel(Injection.provideGetTransactionRepository(context)) as T
            }
        }
    }
}