package com.c23ps422.reclothes.ui.screen.auth.register

import Meta
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.api.ApiConfig
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.pref.ReClothesPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RegisterViewModel(private val pref: ReClothesPreference, context: Application) :
    AndroidViewModel(context) {
    private val _uiState: MutableStateFlow<UiState<Meta>> = MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<Meta>> get() = _uiState

    fun postRegister(
        username: String,
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                _uiState.emit(UiState.Loading)
                val response = ApiConfig.getApiService(getApplication()).register(username, email, password, passwordConfirmation,"1")
                val meta = response.meta
                val data = response.data
                pref.saveToken(data.accessToken)
                emit(meta)
            }.catch {e ->
                _uiState.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState.value = UiState.Success(data)
            }
        }
    }

    companion object {
        fun provideFactory(
            pref: ReClothesPreference,
            context: Application
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RegisterViewModel(pref, context) as T
            }
        }
    }
}