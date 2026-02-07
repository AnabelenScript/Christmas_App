package com.example.deseos_navideos.features.login.data.repositories

import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.login.data.datasources.mapper.toDomain
import com.example.deseos_navideos.features.login.data.datasources.model.LoginRequest
import com.example.deseos_navideos.features.login.data.datasources.model.LoginRes
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterReq
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterRes
import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val deseosApi: DeseosApi,
    private val dataStorage: DataStorage
) : AuthRepository {

    override suspend fun login(username: String, password: String): User {
        val response: LoginRes = deseosApi.login(LoginRequest(username, password))
        dataStorage.saveLoginResponse(response)
        return response.user.toDomain()
    }

    override suspend fun register(username: String, age: Int, country: String, password: String): Int {
        val response: RegisterRes = deseosApi.register(RegisterReq(username, age, country, password))
        return response.id
    }
}
