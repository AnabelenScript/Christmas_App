package com.example.deseos_navideos.features.usuarios.domain.usecases

import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import java.io.File

class UploadAudioUseCase(
    private val repo: UsersRepository
) {

    suspend operator fun invoke(
        id: Int,
        audioFile: File,
        user_id: Int,
        role: String
    ): Result<String> {

        return try {

            val url = repo.uploadAudio(id, audioFile, user_id, role)

            Result.success(url)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}