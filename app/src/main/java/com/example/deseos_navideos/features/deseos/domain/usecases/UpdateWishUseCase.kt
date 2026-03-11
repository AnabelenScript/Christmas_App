package com.example.deseos_navideos.features.deseos.domain.usecases

import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository

class UpdateWishUseCase(
    private val repo: WishesRepository
) {

    suspend operator fun invoke(
        id: Int,
        thing: String,
        user_id: Int,
        role: String
    ): Result<Unit> {

        return try {

            repo.updateWish(id, thing, user_id, role)

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}