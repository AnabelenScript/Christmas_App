package com.example.deseos_navideos.features.detalles.domain.usecases

import com.example.deseos_navideos.features.detalles.domain.repositories.DetailsRepository
import javax.inject.Inject

class UpdateWishStateUseCase @Inject constructor(
    private val repo: DetailsRepository
) {
    suspend operator fun invoke(
        id: Int,
        state: String,
        userId: Int,
        role: String
    ): Result<Unit> {
        return try {
            repo.updateWishState(id, state, userId, role)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
