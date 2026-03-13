package com.example.deseos_navideos.features.deseos.presentation.screens

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
data class WishesUiState(
    val isLoading: Boolean = false,
    val wishes: List<Wish> = emptyList(),
    val newWish: String = "",
    val errorMessage: String? = null,
    val isRecording: Boolean = false,
    val recordingDuration: Int = 0,
    val showSuccessModal: Boolean = false
)