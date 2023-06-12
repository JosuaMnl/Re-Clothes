package com.c23ps422.reclothes.ui.screen.login

import Meta
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.api.ApiConfig
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.ReClothesPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class LoginViewModel(private val pref: ReClothesPreference): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Meta>> = MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<Meta>> get() = _uiState

    fun postLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                _uiState.emit(UiState.Loading)
                val response = ApiConfig.getApiService().login(email, password)
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
            pref: ReClothesPreference
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(pref) as T
            }
        }
    }

}