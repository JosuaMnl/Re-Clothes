package com.c23ps422.reclothes.ui.screen.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.model.response.UserProfileResponse
import com.c23ps422.reclothes.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel(){

    private val _uiState: MutableStateFlow<UiState<UserProfileResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<UserProfileResponse>> get() = _uiState

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            repository.getUser().catch {e ->
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
                return UserViewModel(Injection.provideUserRepository(context)) as T
            }
        }
    }
}