package com.example.deseos_navideos.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkAPIMainModule {
    @Provides
    @Singleton
    @APIMainRetrofit
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://100.50.208.4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}