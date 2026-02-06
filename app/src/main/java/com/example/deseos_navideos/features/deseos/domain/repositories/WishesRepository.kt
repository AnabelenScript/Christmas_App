package com.example.deseos_navideos.features.deseos.domain.repositories

import com.example.deseos_navideos.features.deseos.domain.entities.Wish

interface WishesRepository {
    suspend fun createWish(thing: String, id_user: Int, role: String)
    suspend fun getWishes(id_user: Int, role: String): List<Wish>
    suspend fun updateWish(thing: String, id: Int, id_user: Int, role: String)
    suspend fun deleteWish(id: Int, id_user: Int, role: String)
}