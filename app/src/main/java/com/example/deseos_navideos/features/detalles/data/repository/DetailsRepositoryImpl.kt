package com.example.deseos_navideos.features.detalles.data.repository

import com.example.deseos_navideos.features.deseos.data.datasources.remote.api.WishesApi
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishStateDto
import com.example.deseos_navideos.features.detalles.data.datasources.remote.api.DetailsApi
import com.example.deseos_navideos.features.detalles.data.datasources.remote.mapper.toDetailsDomain
import com.example.deseos_navideos.features.detalles.domain.entities.KidDetails
import com.example.deseos_navideos.features.detalles.domain.repositories.DetailsRepository
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsApi: DetailsApi,
    private val wishesApi: WishesApi
) : DetailsRepository {

    override suspend fun getKidDetails(
        familyCode: String,
        userId: Int,
        role: String,
        kidId: Int
    ): KidDetails? {
        val response = detailsApi.getKidDetails(userId, role, familyCode)
        return response.kids.find { it.id == kidId }?.toDetailsDomain()
    }

    override suspend fun updateWishState(
        id: Int,
        state: String,
        userId: Int,
        role: String
    ) {
        // Usamos el nuevo endpoint en detailsApi que tiene la ruta wishes/state/{id}
        detailsApi.updateWishState(
            userId,
            role,
            id,
            UpdateWishStateDto(state)
        )
    }
}
