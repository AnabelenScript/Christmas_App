package com.example.deseos_navideos.features.deseos.domain.usecases


import android.util.Log
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository

class CreateWishUseCase(private val wishesRepository: WishesRepository) {
    suspend operator fun invoke(thing: String, userId: Int, role: String) =
        wishesRepository.createWish(thing, userId, role)
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
    suspend operator fun invoke(code: String, userId: Int, role: String): Result<List<Wish>> {
        return try {
            val wishes = repo.getWishes(code, userId, role)
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
    suspend operator fun invoke(id: Int, thing: String, userId: Int, role: String) =
        wishesRepository.updateWish(id, thing, userId, role)
}

class UpdateWishStateUseCase(private val wishesRepository: WishesRepository) {
    suspend operator fun invoke(id: Int, state: String, userId: Int, role: String) =
        wishesRepository.updateWishState(id, state, userId, role)
}
