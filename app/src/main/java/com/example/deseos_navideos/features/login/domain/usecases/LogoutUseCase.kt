package com.example.deseos_navideos.features.login.domain.usecases

import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository

class LogoutUseCase(
    private val repo: AuthRepository
) {

    operator fun invoke() {
        repo.logout()
    }
}