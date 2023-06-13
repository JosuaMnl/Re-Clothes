package com.c23ps422.reclothes.ui.screen.login

import Meta
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
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

/**
 * The LoginViewModel class is a ViewModel class responsible for handling user login
 * and managing related UI states in the application.
 * It takes advantage of the AndroidViewModel superclass to have application context access.
 *
 * @property pref an instance of the ReClothesPreference class which provides access to
 * the user preferences.
 * @property _uiState a MutableStateFlow<UiState<Meta>> that represents the current
 * UI state (which can be Loading, Error or Success).
 * @property uiState a public getter for _uiState, but the type is non-mutable StateFlow
 * for encapsulation.
 * @function postLogin takes in an email and password, initiates the login request using
 * the ApiService, and updates _uiState based on the response. In case of a successful response,
 * it also saves the access token in user preferences.
 */
class LoginViewModel(private val pref: ReClothesPreference, context: Application): AndroidViewModel(context) {
    private val _uiState: MutableStateFlow<UiState<Meta>> = MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<Meta>> get() = _uiState

    fun postLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                _uiState.emit(UiState.Loading)
                val response = ApiConfig.getApiService(getApplication()).login(email, password)
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

    /**
     *  * The companion object provides a Factory method for creating instances of LoginViewModel.
     * The factory method allows us to pass parameters to the ViewModel.
     *
     * @property provideFactory a companion object function that creates a ViewModelProvider.Factory
     * for creating instances of LoginViewModel with specified arguments.
     */
    companion object {
        fun provideFactory(
            pref: ReClothesPreference,
            context: Application
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(pref, context) as T
            }
        }
    }
}