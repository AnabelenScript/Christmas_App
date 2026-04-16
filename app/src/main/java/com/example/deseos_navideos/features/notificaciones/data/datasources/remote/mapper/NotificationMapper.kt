package com.example.deseos_navideos.features.notificaciones.data.datasources.remote.mapper

import com.example.deseos_navideos.features.notificaciones.data.datasources.remote.model.NotificationDto
import com.example.deseos_navideos.features.notificaciones.domain.entities.Notification

fun NotificationDto.toDomain(): Notification {
    return Notification(
        id = id,
        title = title,
        body = body,
        idUser = idUser
    )
}
