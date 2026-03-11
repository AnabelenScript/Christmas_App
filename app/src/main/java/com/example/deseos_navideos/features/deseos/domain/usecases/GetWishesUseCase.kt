package com.example.deseos_navideos.features.deseos.domain.usecases

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import javax.inject.Inject

class GetWishesUseCase @Inject constructor(
    private val repo: WishesRepository
) {

    suspend operator fun invoke(
        familyCode: String,
        user_id: Int,
        role: String
    ): Result<List<Wish>> {

        return try {

            val wishes = repo.getWishes(familyCode, user_id, role)

            Result.success(wishes)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}