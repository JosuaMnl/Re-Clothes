package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.response.CreateTransactionItemResponse
import com.c23ps422.reclothes.repository.CreateTransactionItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateTransactionItemViewModel(
    private val repository: CreateTransactionItemRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CreateTransactionItemResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<CreateTransactionItemResponse>> get() = _uiState

    fun createTransactionItem(
        transaction_id: String,
        cloth_id: String
    ) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.createTransactionItem(transaction_id, cloth_id).catch { e ->
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
                return CreateTransactionItemViewModel(Injection.provideCreateTransactionItemRepository(context)) as T
            }
        }
    }
}