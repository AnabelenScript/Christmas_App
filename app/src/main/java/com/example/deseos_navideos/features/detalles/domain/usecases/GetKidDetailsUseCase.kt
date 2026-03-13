package com.example.deseos_navideos.features.detalles.domain.usecases

import com.example.deseos_navideos.features.detalles.domain.entities.KidDetails
import com.example.deseos_navideos.features.detalles.domain.repositories.DetailsRepository
import javax.inject.Inject

class GetKidDetailsUseCase @Inject constructor(
    private val repository: DetailsRepository
) {
    suspend operator fun invoke(familyCode: String, userId: Int, role: String, kidId: Int): Result<KidDetails> {
        return try {
            val kid = repository.getKidDetails(familyCode, userId, role, kidId)
            if (kid != null) {
                Result.success(kid)
            } else {
                Result.failure(Exception("Niño no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
