package com.example.deseos_navideos.features.usuarios.presentation.screens

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid

data class KidsUiState(
    val isLoading: Boolean = false,
    val kids: List<Kid> = emptyList(),
    val wishesByKid: Map<Int, List<Wish>> = emptyMap(),
    val errorMessage: String? = null
)