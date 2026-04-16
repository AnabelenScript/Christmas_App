package com.example.deseos_navideos.features.notificaciones.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.features.notificaciones.domain.usecases.GetNotificationsUseCase
import com.example.deseos_navideos.features.notificaciones.presentation.screens.NotificationsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val userId = UserSession.getUserId()
            val role = UserSession.getRole()

            if (userId == null || role == null) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Sesión no válida") }
                return@launch
            }

            try {
                val result = getNotificationsUseCase(userId, role)
                _uiState.update { it.copy(notifications = result, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}
