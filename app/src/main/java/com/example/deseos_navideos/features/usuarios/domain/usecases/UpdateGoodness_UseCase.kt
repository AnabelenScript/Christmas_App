package com.example.deseos_navideos.features.usuarios.domain.usecases

import android.util.Log
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class UpdateGoodness_UseCase (
    private val repo: UsersRepository
){
    suspend operator fun invoke(id: Int, user_id: Int, role: String){
        try {
            repo.updateGoodness(id, user_id, role)
        } catch (e: Exception){
            Log.d("ERROR_UC", e.toString())
        }
    }
}