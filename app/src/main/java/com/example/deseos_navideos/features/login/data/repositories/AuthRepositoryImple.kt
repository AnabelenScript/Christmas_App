package com.example.deseos_navideos.features.login.data.repositories

import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.features.login.data.datasources.api.AuthApi
import com.example.deseos_navideos.features.login.data.datasources.mapper.toDomain
import com.example.deseos_navideos.features.login.data.datasources.model.LoginRequestDto
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterUserDto
import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val api: AuthApi
) : AuthRepository {

    override suspend fun login(
        username: String,
        password: String
    ): User {

        val response = api.login(
            LoginRequestDto(
                username = username,
                password = password
            )
        )

        val user = response.user.toDomain()

        UserSession.login(
            id = user.id,
            username = user.username,
            role = user.role,
            familyCode = user.familyCode
        )

        return user
    }

    override suspend fun register(
        username: String,
        age: Int,
        country: String,
        password: String,
        role: String,
        familyCode: String?
    ): Int {

        val response = api.register(
            RegisterUserDto(
                username = username,
                age = age,
                country = country,
                password = password,
                role = role,
                familyCode = familyCode
            )
        )

        return response.id
    }

    override fun logout() {
        UserSession.logout()
    }
}