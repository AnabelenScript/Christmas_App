package com.example.deseos_navideos.features.detalles.data.di

import com.example.deseos_navideos.core.di.APIMainRetrofit
import com.example.deseos_navideos.features.detalles.data.datasources.remote.api.DetailsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailsNetworkModule {

    @Provides
    @Singleton
    fun provideDetailsApi(@APIMainRetrofit retrofit: Retrofit): DetailsApi {
        return retrofit.create(DetailsApi::class.java)
    }
}
