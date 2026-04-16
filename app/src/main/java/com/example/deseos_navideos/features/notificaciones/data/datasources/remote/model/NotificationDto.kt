package com.example.deseos_navideos.features.notificaciones.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class NotificationsResponseDto(
    val notifications: List<NotificationDto>
)

data class NotificationDto(
    val id: Int,
    val title: String,
    val body: String,
    @SerializedName("id_user")
    val idUser: Int
)
