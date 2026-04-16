package com.example.deseos_navideos.features.notificaciones.domain.repositories

import com.example.deseos_navideos.features.notificaciones.domain.entities.Notification

interface NotificationsRepository {
    suspend fun getNotifications(userId: Int, role: String): List<Notification>
}
