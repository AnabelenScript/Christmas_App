package com.example.deseos_navideos.features.deseos.data.repositories

import android.util.Log
import com.example.deseos_navideos.core.database.daos.WishDao
import com.example.deseos_navideos.core.database.entities.WishEntity
import com.example.deseos_navideos.features.deseos.data.datasources.local.mapper.toDomain
import com.example.deseos_navideos.features.deseos.data.datasources.local.mapper.toEntity
import com.example.deseos_navideos.features.deseos.data.datasources.remote.api.WishesApi
import com.example.deseos_navideos.features.deseos.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.CreateWishDto
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishDto
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishStateDto
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class WishesRepositoryImpl @Inject constructor(
    private val api: WishesApi,
    private val wishesDao: WishDao
) : WishesRepository {

    override suspend fun createWish(
        thing: String,
        userId: Int,
        role: String
    ) {

        try {

            api.addWish(
                userId,
                role,
                CreateWishDto(thing)
            )

        } catch (e: Exception) {

            val localWish = WishEntity(
                id = 0,
                wish = thing,
                idUser = userId,
                username = null,
                state = "Pendiente",
                photoUrl = null,
                syncState = "PENDING"
            )

            wishesDao.insertWish(localWish)
        }
    }

    override suspend fun syncPendingWishes(
        userId: Int,
        role: String
    ) {

        val pending = wishesDao.getPendingWishes()

        pending.forEach { wish ->

            try {

                val response = api.addWish(
                    userId,
                    role,
                    CreateWishDto(wish.wish)
                )

                wishesDao.markAsSynced(wish.id)

            } catch (e: Exception) {
                Log.d("SYNC_ERROR", "Error al sincronizar el deseo: ${e}")
            }
        }
    }

    override suspend fun getWishes(
        code: String,
        userId: Int,
        role: String
    ): List<Wish> {

        return try {

            val response = api.getWishes(
                userId,
                role,
                code
            )

            val wishes = response.wishes.map { it.toDomain() }

            wishesDao.clear()

            wishesDao.insertAll(
                response.wishes.map { it.toEntity() }
            )

            wishes

        } catch (e: Exception) {

            wishesDao.getAll().map { it.toDomain() }
        }
    }

    override suspend fun updateWish(
        id: Int,
        thing: String,
        userId: Int,
        role: String
    ) {

        api.updateWish(
            userId,
            role,
            id,
            UpdateWishDto(thing)
        )
    }

    override suspend fun deleteWish(
        id: Int,
        userId: Int,
        role: String
    ) {

        api.deleteWish(
            userId,
            role,
            id
        )
    }

    override suspend fun uploadWishPhoto(
        id: Int,
        photoFile: File,
        userId: Int,
        role: String
    ): String {

        val requestFile =
            photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())

        val body =
            MultipartBody.Part.createFormData(
                "photo",
                photoFile.name,
                requestFile
            )

        val response = api.uploadPhoto(
            userId,
            role,
            id,
            body
        )

        return response.url
    }
}