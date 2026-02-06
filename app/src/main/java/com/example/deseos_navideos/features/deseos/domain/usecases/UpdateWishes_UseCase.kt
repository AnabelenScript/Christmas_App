package com.example.deseos_navideos.features.deseos.domain.usecases

import android.util.Log
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository

class UpdateWishes_UseCase (
    private val repo: WishesRepository
) {
    suspend operator fun invoke(id: Int, thing: String, user_id: Int, role: String){
        try {
            repo.updateWish(thing, id, user_id, role)
        } catch (e: Exception) {
            Log.d("ERROR_UC", e.toString())
        }
    }
}