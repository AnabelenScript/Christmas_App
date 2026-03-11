package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class UpdateUserUseCase(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        user_id: Int,
        role: String
    ): Result<Unit> {

        return try {

            repo.updateUser(
                id,
                username,
                age,
                country,
                password,
                user_id,
                role
            )

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}