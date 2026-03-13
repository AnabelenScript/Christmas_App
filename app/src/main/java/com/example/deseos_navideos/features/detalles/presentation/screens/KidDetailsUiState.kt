package com.example.deseos_navideos.features.detalles.presentation.screens

import com.example.deseos_navideos.features.detalles.domain.entities.KidDetails

data class KidDetailsUiState(
    val isLoading: Boolean = false,
    val kid: KidDetails? = null,
    val errorMessage: String? = null
)
