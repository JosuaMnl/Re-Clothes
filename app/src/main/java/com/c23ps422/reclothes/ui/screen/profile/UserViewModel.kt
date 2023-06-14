package com.c23ps422.reclothes.ui.screen.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.api.ApiService
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.data.ReClothesPreference
import com.c23ps422.reclothes.model.response.UserProfileResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(
    private val apiService: ApiService,
    private val pref: ReClothesPreference,
    context: Application
) :
    AndroidViewModel(context) {

    private val _userProfileState = MutableStateFlow<UiState<UserProfileResponse>>(UiState.Loading)
    val userProfileState: StateFlow<UiState<UserProfileResponse>> get() = _userProfileState

    fun getUser() {
        viewModelScope.launch {
            try {
                val userProfileResponse = apiService.getUser()
                _userProfileState.value = UiState.Success(userProfileResponse)
            } catch (e: Exception) {
                _userProfileState.value = UiState.Error("An error has occurred: ${e.message}")
            }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            pref.getToken()
        }
    }

    companion object {
        fun provideFactory(
            apiService: ApiService,
            pref: ReClothesPreference,
            context: Application
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(apiService, pref, context) as T
            }
        }
    }
}