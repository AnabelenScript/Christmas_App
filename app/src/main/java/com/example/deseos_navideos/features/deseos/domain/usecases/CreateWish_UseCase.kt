package com.example.deseos_navideos.features.deseos.domain.usecases

import android.util.Log
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import java.lang.Exception

class CreateWish_UseCase (
    private val repo: WishesRepository
) {
    suspend operator fun invoke(thing: String, id_user: Int, role: String){
        try {
            repo.createWish(thing, id_user, role)
        } catch (e: Exception) {
            Log.d("ERROR_UC", e.toString())
        }
    }
}