package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        id: Int,
        user_id: Int,
        role: String
    ): Result<User> {

        return try {

            val user = repo.getOneUser(id, user_id, role)

            Result.success(user)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}