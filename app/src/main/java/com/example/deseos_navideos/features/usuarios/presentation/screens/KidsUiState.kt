package com.example.deseos_navideos.features.usuarios.presentation.screens

import com.example.deseos_navideos.features.usuarios.domain.entities.Users

data class KidsUiState(
    val isLoading: Boolean = true,
    val kids: List<Users> = emptyList(),
    val errorMessage: String? = null,
)
