package com.c23ps422.reclothes.ui.screen.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.model.response.UserProfileResponse
import com.c23ps422.reclothes.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<UserProfileResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<UserProfileResponse>> get() = _uiState

    private val _uiState2: MutableStateFlow<UiState<UserProfileResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState2: StateFlow<UiState<UserProfileResponse>> get() = _uiState2

    fun getUser() {
        viewModelScope.launch {
            repository.getUser().catch { e ->
                _uiState.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState.value = UiState.Success(data)
            }
        }
    }

    fun updateUser(
        name: String,
        email: String,
        account_number: String,
        address: String,
        phone_number: String,
        account_type: String
    ) {
        viewModelScope.launch {
            _uiState2.emit(UiState.Loading)
            repository.updateUser(
                name = name,
                email = email,
                account_number = account_number,
                address = address,
                phone_number = phone_number,
                account_type = account_type
            ).catch { e ->
                _uiState2.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState2.value = UiState.Success(data)
            }
        }
    }

    val userId: String
        get() = when (val state = uiState.value) {
            is UiState.Success -> state.data.data.id
            else -> "userId Not fetched!"
        }

    val username: String
        get() = when (val state = uiState.value) {
            is UiState.Success -> state.data.data.name
            else -> "name not fetched!"
        }

    companion object {
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(Injection.provideUserRepository(context)) as T
            }
        }
    }
}