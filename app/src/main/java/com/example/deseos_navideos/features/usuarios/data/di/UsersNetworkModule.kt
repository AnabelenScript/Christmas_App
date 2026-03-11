package com.example.deseos_navideos.features.usuarios.data.di

import com.example.deseos_navideos.core.di.APIMainRetrofit
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersNetworkModule {
    @Provides
    @Singleton
    fun providesUsersApi(@APIMainRetrofit retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }
}