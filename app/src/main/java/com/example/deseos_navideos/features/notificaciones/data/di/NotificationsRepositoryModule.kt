package com.example.deseos_navideos.features.notificaciones.data.di

import com.example.deseos_navideos.features.notificaciones.data.repositories.NotificationsRepositoryImpl
import com.example.deseos_navideos.features.notificaciones.domain.repositories.NotificationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationsRepositoryModule {
    @Binds
    abstract fun bindNotificationsRepository(
        notificationsRepositoryImpl: NotificationsRepositoryImpl
    ): NotificationsRepository
}
