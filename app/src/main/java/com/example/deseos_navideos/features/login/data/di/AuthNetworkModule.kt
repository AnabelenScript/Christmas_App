package com.example.deseos_navideos.features.login.data.di

import com.example.deseos_navideos.core.di.APIMainRetrofit
import com.example.deseos_navideos.features.login.data.datasources.api.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {
    @Provides
    @Singleton
    fun provideAuthApi(@APIMainRetrofit retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}