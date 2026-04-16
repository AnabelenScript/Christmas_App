package com.example.deseos_navideos.features.notificaciones.data.datasources.remote.api

import com.example.deseos_navideos.features.notificaciones.data.datasources.remote.model.NotificationsResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationsApi {
    @GET("notifications/")
    suspend fun getNotifications(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String
    ): NotificationsResponseDto
}
