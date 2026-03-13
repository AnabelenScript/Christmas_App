package com.example.deseos_navideos.features.usuarios.domain.repositories

import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid
import com.example.deseos_navideos.features.usuarios.domain.entities.FamilyDashboard
import java.io.File

interface UsersRepository {

    suspend fun getKids(
        familyCode: String,
        userId: Int,
        role: String
    ): FamilyDashboard

    suspend fun getOneUser(
        id: Int,
        userId: Int,
        role: String
    ): User

    suspend fun updateUser(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        userId: Int,
        role: String
    )

    suspend fun deleteUser(
        id: Int,
        userId: Int,
        role: String
    )

    suspend fun uploadAudio(
        id: Int,
        audioFile: File,
        userId: Int,
        role: String
    ): String
}