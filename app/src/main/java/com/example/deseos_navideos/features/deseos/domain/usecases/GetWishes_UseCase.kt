package com.example.deseos_navideos.features.deseos.domain.usecases

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository

class GetWishes_UseCase (
    private val repo: WishesRepository
) {
    suspend operator fun invoke(user_id: Int, role: String): Result<List<Wish>>{
        return try {
            val wishes = repo.getWishes(user_id, role)
            if(wishes.isEmpty())
                Result.failure(Exception("No se encontraron deseos"))
            else
                Result.success(wishes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}