package com.example.deseos_navideos.features.login.data.di

import com.example.deseos_navideos.features.login.data.repositories.AuthRepositoryImpl
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}