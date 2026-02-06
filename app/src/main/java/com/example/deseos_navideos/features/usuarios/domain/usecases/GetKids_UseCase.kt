package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import java.lang.Exception

class GetKids_UseCase (
    private val repo: UsersRepository
) {
    suspend operator fun invoke(user_id: Int, role: String): Result<List<Users>> {
        return try {
            val r = repo.getKids(user_id, role)
            if (r.isEmpty())
                Result.failure(Exception("No se encontraron niños"))
            else
                Result.success(r)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}