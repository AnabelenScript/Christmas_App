package com.example.deseos_navideos.features.detalles.domain.repositories

import com.example.deseos_navideos.features.detalles.domain.entities.KidDetails

interface DetailsRepository {
    suspend fun getKidDetails(familyCode: String, userId: Int, role: String, kidId: Int): KidDetails?
    suspend fun updateWishState(id: Int, state: String, userId: Int, role: String)
}
