package com.example.deseos_navideos.features.deseos.data.di

import com.example.deseos_navideos.core.di.APIMainRetrofit
import com.example.deseos_navideos.features.deseos.data.datasources.remote.api.WishesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WishesNetworkModule {
    @Provides
    @Singleton
    fun providesWishesApi(@APIMainRetrofit retrofit: Retrofit): WishesApi {
        return retrofit.create(WishesApi::class.java)
    }
}