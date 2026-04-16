package com.example.deseos_navideos.features.notificaciones.domain.usecases

import com.example.deseos_navideos.features.notificaciones.domain.entities.Notification
import com.example.deseos_navideos.features.notificaciones.domain.repositories.NotificationsRepository
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val repository: NotificationsRepository
) {
    suspend operator fun invoke(userId: Int, role: String): List<Notification> {
        return repository.getNotifications(userId, role)
    }
}
