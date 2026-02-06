package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class GetOneUser_UseCase (
    private val repo: UsersRepository
) {
    suspend operator fun invoke(
        id: Int,
        user_id: Int,
        role: String
    ): Result<Users>{
        return try {
            val r = repo.getOneUser(id, user_id, role)
            Result.success(r)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}