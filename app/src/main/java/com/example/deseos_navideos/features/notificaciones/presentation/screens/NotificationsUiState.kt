package com.example.deseos_navideos.features.notificaciones.presentation.screens

import com.example.deseos_navideos.features.notificaciones.domain.entities.Notification

data class NotificationsUiState(
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
