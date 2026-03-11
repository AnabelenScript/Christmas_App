package com.example.deseos_navideos.features.login.domain.repositories

import com.example.deseos_navideos.features.login.domain.entities.User

interface AuthRepository {
    suspend fun login(username: String, password: String): User
    suspend fun register(
        username: String,
        age: Int,
        country: String,
        password: String,
        role: String,
        familyCode: String?
    ): Int

    fun logout()
}