package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.entities.FamilyDashboard
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import javax.inject.Inject

class GetKidsUseCase @Inject constructor(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        familyCode: String,
        user_id: Int,
        role: String
    ): Result<FamilyDashboard> {

        return try {

            val dashboard = repo.getKids(familyCode, user_id, role)

            Result.success(dashboard)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}