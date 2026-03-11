package com.example.deseos_navideos.features.usuarios.data.repository

import android.util.Log
import com.example.deseos_navideos.features.login.data.datasources.mapper.toDomain
import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.api.UsersApi
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UpdateUserDto
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UsersRepositoryImpl(
    private val api: UsersApi
) : UsersRepository {

    override suspend fun getKids(
        familyCode: String,
        userId: Int,
        role: String
    ): List<Kid> {

        val response = api.getFamilyDashboard(
            userId,
            role,
            familyCode
        )

        return response.kids.map { it.toDomain() }
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

        val requestFile =
            audioFile.asRequestBody("audio/mpeg".toMediaTypeOrNull())

        val body =
            MultipartBody.Part.createFormData(
                "audio",
                audioFile.name,
                requestFile
            )

        val response = api.uploadAudio(
            userId,
            role,
            id,
            body
        )

        return response.url
    }
}