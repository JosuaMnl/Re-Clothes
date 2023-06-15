package com.c23ps422.reclothes.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.model.response.CreateUserClothResponse
import com.c23ps422.reclothes.repository.CreateUserClothRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateUserClothViewModel(
    private val repository: CreateUserClothRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<CreateUserClothResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<CreateUserClothResponse>> get() = _uiState

    fun createUserCloth(user_id: String, amount_of_clothes: String) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.createUserCloth(user_id, amount_of_clothes).catch { e ->
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
                return CreateUserClothViewModel(Injection.provideCreateUserClothRepository(context)) as T
            }
        }
    }
}