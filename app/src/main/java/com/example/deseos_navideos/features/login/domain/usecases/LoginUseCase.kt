package com.example.deseos_navideos.features.login.domain.usecases

import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository

class LoginUseCase(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): Result<User> {

        return try {

            val user = repo.login(username, password)

            Result.success(user)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}