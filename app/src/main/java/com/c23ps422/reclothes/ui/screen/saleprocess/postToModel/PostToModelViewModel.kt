package com.c23ps422.reclothes.ui.screen.saleprocess.postToModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.c23ps422.reclothes.common.UiState
import com.c23ps422.reclothes.di.Injection
import com.c23ps422.reclothes.model.response.UploadClothResponse
import com.c23ps422.reclothes.repository.PostToModelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PostToModelViewModel(
    private val repository: PostToModelRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UploadClothResponse>> =
        MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState<UploadClothResponse>> get() = _uiState

    fun createClothImage(file: File, user_cloth_id: String) {
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("original_image", file.name, requestFile)
        val id = RequestBody.create("text/plain".toMediaTypeOrNull(), user_cloth_id)

        viewModelScope.launch {
            _uiState.emit(UiState.Loading)
            repository.createClothImage(body, id).catch {e ->
                _uiState.value = UiState.Error(e.message.toString())
            }.collect { data ->
                _uiState.value = UiState.Success(data)
            }
        }
    }

    companion object{
        fun provideFactory(
             context: Context,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PostToModelViewModel(Injection.providePostToModelRepository(context)) as T
            }
        }
    }
}