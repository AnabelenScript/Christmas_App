package com.example.deseos_navideos.features.deseos.data.repositories

import android.util.Log
import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.features.deseos.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishesReq
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class WishesRepositoryImpl (
    private val api: DeseosApi
): WishesRepository {
    override suspend fun createWish(thing: String, user_id: Int, role: String) {
        api.createWish(WishesReq(thing), user_id, role)
    }

    override suspend fun getWishes(code: String, user_id: Int, role: String): List<Wish> {
        val response = api.getWishes(code, user_id, role)
        return response.wishes.map { it.toDomain() }
    }

    override suspend fun updateWish(id: Int, thing: String, user_id: Int, role: String) {
        api.updateWish(id, WishesReq(thing), user_id, role)
    }

    override suspend fun updateWishState(id: Int, state: String, user_id: Int, role: String) {
        api.updateWishState(id, mapOf("state" to state), user_id, role)
    }

    override suspend fun deleteWish(id: Int, user_id: Int, role: String) {
        api.deleteWish(id, user_id, role)
    }

    override suspend fun uploadWishPhoto(id: Int, photoFile: File, user_id: Int, role: String): String {
        val requestFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("photo", photoFile.name, requestFile)
        val response = api.uploadWishPhoto(id, body, user_id, role)
        return response.message
    }
}
