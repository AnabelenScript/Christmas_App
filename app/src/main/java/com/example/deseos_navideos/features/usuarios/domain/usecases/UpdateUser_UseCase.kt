package com.example.deseos_navideos.features.usuarios.domain.usecases

import android.util.Log
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class UpdateUser_UseCase (
    private val repo: UsersRepository
) {
    suspend operator fun invoke(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        user_id: Int,
        role: String,
    ){
        try {
            repo.updateUser(
                id,
                username,
                age,
                country,
                password,
                user_id,
                role
            )
        } catch (e: Exception){
            Log.d("ERROR_UC", e.toString())
        }
    }
}