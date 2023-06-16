package com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.response.UpdateTransactionResponse
import com.c23ps422.reclothes.repository.UpdateTransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class Transaction(
    val price: Int,
    val weight: String,
    val quantity: String,
    val status: String,
    val date: String
)

class UpdateTransactionViewModel(
    private val repository: UpdateTransactionRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<UpdateTransactionResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<UpdateTransactionResponse>> get() = _uiState

    private val _transaction: MutableStateFlow<Transaction> =
        MutableStateFlow(Transaction(price = 0, weight = "", quantity = "", status = "", date = ""))
    val transaction: StateFlow<Transaction> get() = _transaction

    fun updateTransaction(
        transaction_id: String,
        weight: String,
        quantity: String,
        date: String,
    ) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.updateTransaction(transaction_id, weight, quantity, date, "PENDING")
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }.collect { data ->
                    val transaction = Transaction(
                        price = data.data.transaction.totalSellingPrice,
                        weight = data.data.transaction.weight,
                        quantity = data.data.transaction.quantity,
                        status = data.data.transaction.status,
                        date = data.data.transaction.createdAt
                    )
                    _transaction.value = transaction
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
                return UpdateTransactionViewModel(
                    Injection.provideUpdateTransactionRepository(
                        context
                    )
                ) as T
            }
        }
    }
}