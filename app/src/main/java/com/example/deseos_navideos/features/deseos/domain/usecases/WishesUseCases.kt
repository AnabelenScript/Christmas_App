package com.example.deseos_navideos.features.deseos.domain.usecases


import android.util.Log
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository

class CreateWishUseCase(private val wishesRepository: WishesRepository) {
    suspend operator fun invoke(description: String, userId: Int, role: String) =
        wishesRepository.createWish(description, userId, role)
}

class DeleteWishUseCase(private val repo: WishesRepository) {
    suspend operator fun invoke(id: Int, userId: Int, role: String) {
        try {
            repo.deleteWish(id, userId, role)
        } catch (e: Exception) {
            Log.d("ERROR_UC", e.toString())
        }
    }
}

class GetWishesUseCase(private val repo: WishesRepository) {
    suspend operator fun invoke(userId: Int, role: String): Result<List<Wish>> {
        return try {
            val wishes = repo.getWishes(userId, role)
            if (wishes.isEmpty())
                Result.failure(Exception("No se encontraron deseos"))
            else
                Result.success(wishes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class UpdateWishesUseCase(private val wishesRepository: WishesRepository) {
    suspend operator fun invoke(id: Int, description: String, userId: Int, role: String) =
        wishesRepository.updateWish(id, description, userId, role)
}
