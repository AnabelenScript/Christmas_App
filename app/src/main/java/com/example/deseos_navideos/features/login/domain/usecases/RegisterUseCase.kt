package com.example.deseos_navideos.features.login.domain.usecases

import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository

class RegisterUseCase(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(
        username: String,
        age: Int,
        country: String,
        password: String,
        role: String,
        familyCode: String?
    ): Result<Int> {

        return try {

            val id = repo.register(
                username,
                age,
                country,
                password,
                role,
                familyCode
            )

            Result.success(id)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}