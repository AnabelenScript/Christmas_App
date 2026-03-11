package com.example.deseos_navideos.features.deseos.domain.repositories

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import java.io.File

interface WishesRepository {

    suspend fun createWish(
        thing: String,
        userId: Int,
        role: String
    )

    suspend fun getWishes(
        code: String,
        userId: Int,
        role: String
    ): List<Wish>

    suspend fun updateWish(
        id: Int,
        thing: String,
        userId: Int,
        role: String
    )

    suspend fun updateWishState(
        id: Int,
        state: String,
        userId: Int,
        role: String
    )

    suspend fun deleteWish(
        id: Int,
        userId: Int,
        role: String
    )

    suspend fun uploadWishPhoto(
        id: Int,
        photoFile: File,
        userId: Int,
        role: String
    ): String
}