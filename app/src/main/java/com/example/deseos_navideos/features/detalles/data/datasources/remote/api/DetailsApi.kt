package com.example.deseos_navideos.features.detalles.data.datasources.remote.api

import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishStateDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.FamilyDashboardDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.MessageResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface DetailsApi {
    @GET("users/family/{code}/dashboard")
    suspend fun getKidDetails(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("code") familyCode: String
    ): FamilyDashboardDto

    @PATCH("wishes/state/{id}")
    suspend fun updateWishState(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int,
        @Body request: UpdateWishStateDto
    ): MessageResponseDto
}
