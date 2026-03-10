package com.example.deseos_navideos.features.usuarios.domain.repositories

import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import java.io.File

interface UsersRepository {
    suspend fun getKids(familyCode: String, user_id: Int, role: String): List<Users>
    suspend fun getOneUser(id: Int, user_id: Int, role: String): Users
    suspend fun updateUser(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        user_id: Int,
        role: String
    )
    suspend fun deleteUser(id: Int, user_id: Int, role: String)
    suspend fun updateGoodness(id: Int, user_id: Int, role: String)
    suspend fun uploadAudio(id: Int, audioFile: File, user_id: Int, role: String): String
}