package com.example.deseos_navideos.features.deseos.domain.repositories

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import java.io.File

interface WishesRepository {
    suspend fun createWish(thing: String, user_id: Int, role: String)
    suspend fun getWishes(code: String, user_id: Int, role: String): List<Wish>
    suspend fun updateWish(id: Int, thing: String, user_id: Int, role: String)
    suspend fun updateWishState(id: Int, state: String, user_id: Int, role: String)
    suspend fun deleteWish(id: Int, user_id: Int, role: String)
    suspend fun uploadWishPhoto(id: Int, photoFile: File, user_id: Int, role: String): String
}
