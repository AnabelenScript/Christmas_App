package com.example.deseos_navideos.features.notificaciones.data.repositories

import com.example.deseos_navideos.features.notificaciones.data.datasources.remote.api.NotificationsApi
import com.example.deseos_navideos.features.notificaciones.data.datasources.remote.mapper.toDomain
import com.example.deseos_navideos.features.notificaciones.domain.entities.Notification
import com.example.deseos_navideos.features.notificaciones.domain.repositories.NotificationsRepository
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    private val api: NotificationsApi
) : NotificationsRepository {
    override suspend fun getNotifications(userId: Int, role: String): List<Notification> {
        return try {
            val response = api.getNotifications(userId, role)
            response.notifications.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
