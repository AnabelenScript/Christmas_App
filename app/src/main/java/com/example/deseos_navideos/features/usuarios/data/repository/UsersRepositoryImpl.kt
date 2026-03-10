package com.example.deseos_navideos.features.usuarios.data.repository

import android.util.Log
import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UserReq
import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UsersRepositoryImpl (
    private val api: DeseosApi
): UsersRepository {
    override suspend fun getKids(
        familyCode: String,
        user_id: Int,
        role: String
    ): List<Users> {
        val r = api.getKids(familyCode, user_id, role)
        // Note: The mapping here might need adjustment based on how 'Users' entity is defined
        // compared to the new dashboard response. For now assuming a simple map.
        return r.kids.map { kid ->
            Users(
                id = kid.id,
                username = kid.username,
                age = 0, // Not provided in dashboard
                country = "", // Not provided in dashboard
                password = ""
            )
        }
    }

    override suspend fun getOneUser(
        id: Int,
        user_id: Int,
        role: String
    ): Users {
        val r = api.getOneUser(id, user_id, role)
        return r.user.toDomain()
    }

    override suspend fun updateUser(
        id: Int,
        username: String,
        age: Int,
        country: String,
        password: String,
        user_id: Int,
        role: String
    ) {
        api.updateUser(
            id,
            UserReq(
                username = username,
                age = age,
                country = country,
                password = password,
            ),
            user_id,
            role
        )
    }

    override suspend fun deleteUser(id: Int, user_id: Int, role: String) {
        api.deleteUser(id, user_id, role)
    }

    override suspend fun updateGoodness(id: Int, user_id: Int, role: String) {
        // Assuming updateGoodness toggles or sets good_kid to 1.
        // Based on UserDTO.good_kid: Int
        api.updateGoodness(id, mapOf("good_kid" to 1), user_id, role)
    }

    override suspend fun uploadAudio(id: Int, audioFile: File, user_id: Int, role: String): String {
        val requestFile = audioFile.asRequestBody("audio/mpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("audio", audioFile.name, requestFile)
        val response = api.uploadAudio(id, body, user_id, role)
        return response.message // Or use the URL if added to GenericResponse
    }
}
