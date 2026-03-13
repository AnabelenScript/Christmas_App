package com.example.deseos_navideos.features.login.domain.usecases

import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repo: AuthRepository
) {

    operator fun invoke() {
        repo.logout()
    }
}