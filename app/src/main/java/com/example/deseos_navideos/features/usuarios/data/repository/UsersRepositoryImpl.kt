package com.example.deseos_navideos.features.usuarios.data.repository

import android.content.Context
import com.example.deseos_navideos.core.database.daos.KidsDao
import com.example.deseos_navideos.core.database.daos.WishDao
import com.example.deseos_navideos.core.database.entities.WishEntity
import com.example.deseos_navideos.features.deseos.data.datasources.local.mapper.toDomain
import com.example.deseos_navideos.features.login.data.datasources.mapper.toDomain
import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.usuarios.data.datasources.local.mapper.toEntity
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.api.UsersApi
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UpdateUserDto
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid
import com.example.deseos_navideos.features.usuarios.domain.entities.FamilyDashboard
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import com.example.deseos_navideos.core.sync.SyncWorker
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val kidDao: KidsDao,
    private val wishDao: WishDao,
    @ApplicationContext private val context: Context
) : UsersRepository {

    override suspend fun getKids(
        familyCode: String,
        userId: Int,
        role: String
    ): FamilyDashboard {

        return try {

            val response = api.getFamilyDashboard(
                userId,
                role,
                familyCode
            )

            val kids = response.kids.map { it.toDomain() }

            kidDao.clear()
            kidDao.insertKids(
                response.kids.map { it.toEntity(familyCode) }
            )

            // Borrar los antiguos
            response.kids.forEach { kid ->
                wishDao.deleteWishesByUser(kid.id)
            }
            
            val wishes = response.kids.flatMap { kid ->
                kid.wishes.map { wish ->
                    wish.toEntity(kid.id)
                }
            }

            wishDao.insertAll(wishes)

            FamilyDashboard(
                familyCode = response.familyCode,
                kids = kids
            )

        } catch (e: Exception) {

            val kids = kidDao.getKids()

            val wishes = wishDao.getAll()

            val kidsList = kids.map { kid ->

                val kidWishes = wishes
                    .filter { it.idUser == kid.id }
                    .map { it.toDomain() }

                Kid(
                    id = kid.id,
                    username = kid.username,
                    audioUrl = kid.audioUrl,
                    wishes = kidWishes
                )
            }
            
            FamilyDashboard(
                familyCode = familyCode,
                kids = kidsList
            )
        }
    }

    override suspend fun getOneUser(
        id: Int,
        userId: Int,
        role: String
    ): User {

        val response = api.getUser(
            userId,
            role,
            id
        )

        return response.user.toDomain()
    }

    override suspend fun updateUser(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        userId: Int,
        role: String
    ) {

        api.updateUser(
            userId,
            role,
            id,
            UpdateUserDto(
                username = username,
                age = age,
                country = country,
                password = password
            )
        )
    }

    override suspend fun deleteUser(
        id: Int,
        userId: Int,
        role: String
    ) {

        api.deleteUser(
            userId,
            role,
            id
        )
    }

    override suspend fun uploadAudio(
        id: Int,
        audioFile: File,
        userId: Int,
        role: String
    ): String {

        return try {
            val requestFile = audioFile.asRequestBody("audio/mpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("audio", audioFile.name, requestFile)
            val response = api.uploadAudio(userId, role, id, body)
            response.url
        } catch (e: Exception) {
            val audioTask = WishEntity(
                id = id,
                wish = "",
                idUser = id,
                username = null,
                state = "",
                photoUrl = null,
                syncState = "PENDING",
                localFilePath = audioFile.absolutePath,
                taskType = "AUDIO"
            )
            wishDao.insertWish(audioTask)
            scheduleSync()
            ""
        }
    }

    override suspend fun updateToken(token: String, userId: Int, role: String) {
        try {
            api.updateToken(userId, role, mapOf("token" to token))
        } catch (e: Exception) {
            // Log error
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