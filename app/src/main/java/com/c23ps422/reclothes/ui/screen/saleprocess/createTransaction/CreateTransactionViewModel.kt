package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.response.CreateTransactionResponse
import com.c23ps422.reclothes.repository.CreateTransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateTransactionViewModel(
    private val repository: CreateTransactionRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CreateTransactionResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<CreateTransactionResponse>> get() = _uiState

    private val _transactionId: MutableStateFlow<String> =
        MutableStateFlow("")
    val transactionId: StateFlow<String> get() = _transactionId

    fun createTransaction() {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.createTransaction().catch {e ->
                _uiState.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState.value = UiState.Success(data)
                _transactionId.value = data.data.transaction.id
            }
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CreateTransactionViewModel(Injection.provideCreateTransactionRepository(context)) as T
            }
        }
    }
}