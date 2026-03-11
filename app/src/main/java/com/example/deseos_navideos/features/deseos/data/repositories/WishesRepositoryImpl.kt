package com.example.deseos_navideos.features.deseos.data.repositories

import android.util.Log
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

class WishesRepositoryImpl(
    private val api: WishesApi
) : WishesRepository {

    override suspend fun createWish(
        thing: String,
        userId: Int,
        role: String
    ) {

        api.addWish(
            userId,
            role,
            CreateWishDto(thing)
        )
    }

    override suspend fun getWishes(
        code: String,
        userId: Int,
        role: String
    ): List<Wish> {

        val response = api.getWishes(
            userId,
            role,
            code
        )

        return response.wishes.map { it.toDomain() }
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

    override suspend fun updateWishState(
        id: Int,
        state: String,
        userId: Int,
        role: String
    ) {

        api.updateWishState(
            userId,
            role,
            id,
            UpdateWishStateDto(state)
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