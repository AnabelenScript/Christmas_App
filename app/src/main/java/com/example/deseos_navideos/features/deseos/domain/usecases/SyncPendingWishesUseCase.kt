package com.example.deseos_navideos.features.deseos.domain.usecases

import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import javax.inject.Inject


class SyncPendingWishesUseCase @Inject constructor(
    private val repository: WishesRepository
) {

    suspend operator fun invoke(
        userId: Int,
        role: String
    ) {
        repository.syncPendingWishes(userId, role)
    }
}