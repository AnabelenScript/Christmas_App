package com.example.deseos_navideos.features.deseos.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UploadWishPhotoUseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UploadAudioUseCase
import com.example.deseos_navideos.features.deseos.presentation.screens.WishesUiState
import com.example.deseos_navideos.core.hardware.camara.domain.CamaraService
import com.example.deseos_navideos.core.hardware.audio.domain.AudioService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WishesViewModel @Inject constructor(
    private val createWishUseCase: CreateWishUseCase,
    private val getWishesUseCase: GetWishesUseCase,
    private val updateWishUseCase: UpdateWishUseCase,
    private val deleteWishUseCase: DeleteWishUseCase,
    private val uploadWishPhotoUseCase: UploadWishPhotoUseCase,
    private val uploadAudioUseCase: UploadAudioUseCase,
    private val camaraService: CamaraService,
    private val audioService: AudioService
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishesUiState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentAudioFile: File? = null

    init {
        loadWishes()
    }

    fun onNewWishChange(value: String) {
        _uiState.update { it.copy(newWish = value) }
    }

    fun loadWishes() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val family_code = UserSession.getFamilyCode()
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if(family_code == null || user_id == null || role == null) return@launch

            val result = getWishesUseCase(family_code, user_id, role)

            result.fold(
                onSuccess = { list ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            wishes = list
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    fun addWish() {
        val wish = _uiState.value.newWish
        if (wish.isBlank()) return
        viewModelScope.launch {
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()
            if(user_id == null || role == null) return@launch
            val result = createWishUseCase(wish, user_id, role)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(newWish = "") }
                    loadWishes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(errorMessage = error.message) }
                }
            )
        }
    }

    fun editWish(id: Int, description: String) {
        if (description.isBlank()) return
        viewModelScope.launch {
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()
            if(user_id == null || role == null) return@launch

            val result = updateWishUseCase(id, description, user_id, role)
            result.fold(
                onSuccess = {
                    loadWishes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(errorMessage = error.message) }
                }
            )
        }
    }

    fun uploadPhoto(wishId: Int, uri: Uri) {
        val file = camaraService.getFileFromUri(uri) ?: return
        
        Log.d("WishesViewModel", "Subiendo foto: wishId=$wishId, file=${file.absolutePath}, name=${file.name}")
        
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()
            
            if(user_id == null || role == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Sesión no válida") }
                return@launch
            }

            val result = uploadWishPhotoUseCase(wishId, file, user_id, role)
            
            result.fold(
                onSuccess = {
                    loadWishes()
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    fun startRecording(cacheDir: File) {
        val fileName = "audio_wish_${System.currentTimeMillis()}.m4a"
        currentAudioFile = File(cacheDir, fileName)
        audioService.startRecording(currentAudioFile!!)
        _uiState.update { it.copy(isRecording = true, recordingDuration = 0) }
        
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { it.copy(recordingDuration = it.recordingDuration + 1) }
            }
        }
    }

    fun stopRecording() {
        audioService.stopRecording()
        timerJob?.cancel()
        _uiState.update { it.copy(isRecording = false) }
    }

    fun uploadAudio() {
        val file = currentAudioFile ?: return
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()
            if(user_id == null || role == null) return@launch

            val result = uploadAudioUseCase(user_id, file, user_id, role)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isLoading = false, recordingDuration = 0) }
                    currentAudioFile = null
                },
                onFailure = { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    fun getNewImageUri(): Uri {
        return camaraService.createImageUri()
    }

    fun removeWish(id: Int) {
        viewModelScope.launch {
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()
            if(user_id == null || role == null) return@launch
            val result = deleteWishUseCase(id, user_id, role)
            result.fold(
                onSuccess = {
                    _uiState.update { state ->
                        state.copy(
                            wishes = state.wishes.filterNot { it.id == id }
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message)
                    }
                }
            )
        }
    }
}
