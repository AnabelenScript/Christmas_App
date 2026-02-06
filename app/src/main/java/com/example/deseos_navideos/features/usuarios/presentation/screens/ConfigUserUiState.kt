package com.example.deseos_navideos.features.usuarios.presentation.screens

import com.example.deseos_navideos.features.usuarios.domain.entities.Users

data class ConfigUserUiState (
    val isLoading: Boolean = false,
    val userInfo: Users?,
    val error: String? = null,
)