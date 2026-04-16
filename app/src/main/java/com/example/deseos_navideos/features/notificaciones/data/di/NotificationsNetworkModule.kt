package com.example.deseos_navideos.features.notificaciones.data.di

import com.example.deseos_navideos.core.di.APIMainRetrofit
import com.example.deseos_navideos.features.notificaciones.data.datasources.remote.api.NotificationsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsNetworkModule {
    @Provides
    @Singleton
    fun providesNotificationsApi(@APIMainRetrofit retrofit: Retrofit): NotificationsApi {
        return retrofit.create(NotificationsApi::class.java)
    }
}
