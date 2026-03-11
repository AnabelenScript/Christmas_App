package com.example.deseos_navideos.features.deseos.domain.usecases

import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import java.io.File
import javax.inject.Inject

class UploadWishPhotoUseCase @Inject constructor(
    private val repo: WishesRepository
) {

    suspend operator fun invoke(
        id: Int,
        photo: File,
        user_id: Int,
        role: String
    ): Result<String> {

        return try {

            val url = repo.uploadWishPhoto(id, photo, user_id, role)

            Result.success(url)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}