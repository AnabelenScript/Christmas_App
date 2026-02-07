package com.example.deseos_navideos.features.deseos.data.repositories

import android.util.Log
import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.features.deseos.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishesReq
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository

class WishesRepositoryImpl (
    private val api: DeseosApi
): WishesRepository {
    override suspend fun createWish(thing: String, id_user: Int, role: String) {
        val response = api.createWish(
            WishesReq(thing),
            id_user,
            role
        )
        Log.d("API Response", response.toString())
    }

    override suspend fun getWishes(id_user: Int, role: String): List<Wish> {
        val response = api.getWishes(id_user, role)
        return response.wishes.map { it.toDomain() }
    }

    override suspend fun updateWish(id: Int, description: String, id_user: Int, role: String) {
        val response = api.updateWish(
            id,
            WishesReq(description),
            id_user,
            role
        )
        Log.d("API Response", response.toString())
    }


    override suspend fun deleteWish(id: Int, id_user: Int, role: String) {
        val response = api.deleteWish(id, id_user, role)
        Log.d("API Response", response.toString())
    }
}