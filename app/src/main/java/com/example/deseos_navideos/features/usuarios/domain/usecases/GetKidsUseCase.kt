package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.entities.Kid
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class GetKidsUseCase(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        familyCode: String,
        user_id: Int,
        role: String
    ): Result<List<Kid>> {

        return try {

            val kids = repo.getKids(familyCode, user_id, role)

            if (kids.isEmpty())
                Result.failure(Exception("No se encontraron niños"))
            else
                Result.success(kids)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}