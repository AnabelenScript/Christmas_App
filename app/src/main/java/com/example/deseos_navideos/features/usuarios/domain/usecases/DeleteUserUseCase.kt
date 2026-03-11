package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class DeleteUserUseCase(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        id: Int,
        user_id: Int,
        role: String
    ): Result<Unit> {

        return try {

            repo.deleteUser(id, user_id, role)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}