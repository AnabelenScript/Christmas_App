package com.example.deseos_navideos.features.login.presentation.screens


import com.example.deseos_navideos.features.login.domain.entities.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val registeredUserId: Int? = null,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)
