package com.c23ps422.reclothes.ui.screen.saleprocess.clothesIdentity

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.helper.UiState
import com.c23ps422.reclothes.model.response.CreateClothResponse
import com.c23ps422.reclothes.repository.ClothesIdentityRepository
import com.c23ps422.reclothes.ui.screen.saleprocess.createTransaction.ClothesItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ClothesIdentityViewModel(
    private val repository: ClothesIdentityRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CreateClothResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<CreateClothResponse>> get() = _uiState

    private val _clothes: MutableStateFlow<List<ClothesItem>> = MutableStateFlow(emptyList())
    val clothes: StateFlow<List<ClothesItem>> = _clothes

    fun createCloth(
        image: String,
        cloth_id: String,
        type: String,
        description: String
    ) {
        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.createCloth(cloth_id, type, description).catch { e ->
                _uiState.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState.value = UiState.Success(data)
                val newCloth = ClothesItem(
                    id = data.data.cloth.id,
                    type = type,
                    description = description,
                    clothImage = image
                )
                _clothes.value = _clothes.value + newCloth
            }
        }
    }

    companion object {
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ClothesIdentityViewModel(Injection.provideClothesIdentityRepository(context)) as T
            }
        }
    }
}