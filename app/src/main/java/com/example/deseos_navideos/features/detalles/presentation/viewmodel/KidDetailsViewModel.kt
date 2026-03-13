package com.example.deseos_navideos.features.detalles.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.hardware.audio.domain.AudioService
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.features.detalles.domain.usecases.GetKidDetailsUseCase
import com.example.deseos_navideos.features.detalles.domain.usecases.UpdateWishStateUseCase
import com.example.deseos_navideos.features.detalles.presentation.screens.KidDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KidDetailsViewModel @Inject constructor(
    private val getKidDetailsUseCase: GetKidDetailsUseCase,
    private val updateWishStateUseCase: UpdateWishStateUseCase,
    private val audioService: AudioService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val kidId: Int = try {
        val id = savedStateHandle.get<Int>("kidId")
        id ?: savedStateHandle.get<String>("kidId")?.toInt() ?: 0
    } catch (e: Exception) {
        0
    }

    private val _uiState = MutableStateFlow(KidDetailsUiState())
    val uiState: StateFlow<KidDetailsUiState> = _uiState

    init {
        loadKidDetails()
    }

    fun loadKidDetails() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val family_code = UserSession.getFamilyCode()
            val user_id = UserSession.getUserId()
            val role = UserSession.getRole()

            if (family_code == null || user_id == null || role == null) {
                Log.e("KidDetailsVM", "Sesión incompleta: code=$family_code, uid=$user_id, role=$role")
                return@launch
            }

            val result = getKidDetailsUseCase(family_code, user_id, role, kidId)

            result.fold(
                onSuccess = { kid ->
                    Log.d("KidDetailsVM", "Deseos recibidos para ${kid.username}:")
                    kid.wishes.forEach { wish ->
                        Log.d("KidDetailsVM", "  - Wish ID: ${wish.id}, Cosa: ${wish.wish}, Estado actual: '${wish.state}'")
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            kid = kid
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("KidDetailsVM", "Error al cargar detalles del niño", error)
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

    fun updateWishState(wishId: Int, newState: String) {
        viewModelScope.launch {
            val user_id = UserSession.getUserId()
            val roleFromSession = UserSession.getRole()

            val role = when(roleFromSession?.lowercase()) {
                "padre" -> "parent"
                "hijo" -> "kid"
                else -> roleFromSession
            }

            Log.d("KidDetailsVM", "Solicitando cambio de estado - wishId: $wishId, nuevoEstado: $newState")

            if (user_id == null || role == null) {
                _uiState.update { it.copy(errorMessage = "Sesión no válida") }
                return@launch
            }

            // Actualización optimista
            _uiState.update { currentState ->
                val updatedKid = currentState.kid?.let { kid ->
                    kid.copy(
                        wishes = kid.wishes.map { wish ->
                            if (wish.id == wishId) wish.copy(state = newState) else wish
                        }
                    )
                }
                currentState.copy(kid = updatedKid)
            }

            val result = updateWishStateUseCase(wishId, newState, user_id, role)

            result.fold(
                onSuccess = {
                    Log.d("KidDetailsVM", "Servidor confirmó cambio para wishId: $wishId. Recargando...")
                    loadKidDetails()
                },
                onFailure = { error ->
                    Log.e("KidDetailsVM", "Error al actualizar en el servidor. Revirtiendo...", error)
                    loadKidDetails()
                    _uiState.update {
                        it.copy(errorMessage = "Error actualizando el deseo: ${error.message}")
                    }
                }
            )
        }
    }

    fun playAudio(url: String) {
        audioService.playAudio(url)
    }

    fun stopAudio() {
        audioService.stopPlayback()
    }

    fun isPlaying(): Boolean {
        return audioService.isPlaying()
    }

    override fun onCleared() {
        super.onCleared()
        audioService.stopPlayback()
    }
}
