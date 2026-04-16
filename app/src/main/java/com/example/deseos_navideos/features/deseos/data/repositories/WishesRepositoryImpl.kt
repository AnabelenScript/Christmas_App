package com.example.deseos_navideos.features.deseos.data.repositories

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.deseos_navideos.core.database.daos.WishDao
import com.example.deseos_navideos.core.database.entities.WishEntity
import com.example.deseos_navideos.core.sync.SyncWorker
import com.example.deseos_navideos.features.deseos.data.datasources.local.mapper.toDomain
import com.example.deseos_navideos.features.deseos.data.datasources.local.mapper.toEntity
import com.example.deseos_navideos.features.deseos.data.datasources.remote.api.WishesApi
import com.example.deseos_navideos.features.deseos.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.CreateWishDto
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishDto
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class WishesRepositoryImpl @Inject constructor(
    private val api: WishesApi,
    private val wishesDao: WishDao,
    @ApplicationContext private val context: Context
) : WishesRepository {

    override suspend fun createWish(
        thing: String,
        userId: Int,
        role: String
    ) {
        try {
            val response = api.addWish(userId, role, CreateWishDto(thing))
            // Si tiene éxito, lo guardamos como sincronizado
            wishesDao.insertWish(
                WishEntity(
                    id = response.id,
                    wish = thing,
                    idUser = userId,
                    username = null,
                    state = "Pendiente",
                    photoUrl = null,
                    syncState = "SYNCED",
                    taskType = "WISH"
                )
            )
        } catch (e: Exception) {
            val localWish = WishEntity(
                id = 0,
                wish = thing,
                idUser = userId,
                username = null,
                state = "Pendiente",
                photoUrl = null,
                syncState = "PENDING",
                taskType = "WISH"
            )
            wishesDao.insertWish(localWish)
            scheduleSync()
        }
    }

    override suspend fun syncPendingWishes(userId: Int, role: String) {
        scheduleSync()
    }

    override suspend fun getWishes(
        code: String,
        userId: Int,
        role: String
    ): List<Wish> {
        return try {
            val response = api.getWishes(userId, role, code)
            val wishes = response.wishes.map { it.toDomain() }

            wishesDao.deleteWishesByUser(userId)
            
            wishesDao.insertAll(
                response.wishes.map { it.toEntity() }
            )
            wishes
        } catch (e: Exception) {
            wishesDao.getWishesByUser(userId).map { it.toDomain() }
        }
    }

    override suspend fun updateWish(id: Int, thing: String, userId: Int, role: String) {
        api.updateWish(userId, role, id, UpdateWishDto(thing))
    }

    override suspend fun deleteWish(id: Int, userId: Int, role: String) {
        api.deleteWish(userId, role, id)
    }

    override suspend fun uploadWishPhoto(
        id: Int,
        photoFile: File,
        userId: Int,
        role: String
    ): String {
        return try {
            val requestFile = photoFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("photo", photoFile.name, requestFile)
            val response = api.uploadPhoto(userId, role, id, body)
            response.url
        } catch (e: Exception) {
            val photoTask = WishEntity(
                id = id, // Asociado a este deseo
                wish = "",
                idUser = userId,
                username = null,
                state = "",
                photoUrl = null,
                syncState = "PENDING",
                localFilePath = photoFile.absolutePath,
                taskType = "PHOTO"
            )
            wishesDao.insertWish(photoTask)
            scheduleSync()
            ""
        }
    }

    private fun scheduleSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(syncRequest)
    }
}