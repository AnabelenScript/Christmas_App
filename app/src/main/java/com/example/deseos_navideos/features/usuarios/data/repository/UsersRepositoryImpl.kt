package com.example.deseos_navideos.features.usuarios.data.repository

import android.util.Log
import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UserReq
import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class UsersRepositoryImpl (
    private val api: DeseosApi
): UsersRepository {
    override suspend fun getKids(
        user_id: Int,
        role: String
    ): List<Users> {
        val r = api.getKids(user_id, role)
        return r.kids.map { it.toDomain() }
    }

    override suspend fun getOneUser(
        id: Int,
        user_id: Int,
        role: String
    ): Users {
        val r = api.getOneUser(id, user_id, role)
        return r.user.toDomain()
    }

    override suspend fun updateUser(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        user_id: Int,
        role: String
    ) {
        val r = api.updateUser(
            id,
            UserReq(
                username = username,
                age = age,
                country = country,
                password = password,
            ),
            user_id,
            role
        )
        Log.d("API_RES", r.toString())
    }

    override suspend fun updateGoodness(id: Int, user_id: Int, role: String) {
        val r = api.updateGoodness(id, user_id, role)
        Log.d("API_RES", r.toString())
    }

    override suspend fun deleteUser(id: Int, user_id: Int, role: String) {
        val r = api.deleteWish(id, user_id, role)
        Log.d("API_RES", r.toString())
    }
}