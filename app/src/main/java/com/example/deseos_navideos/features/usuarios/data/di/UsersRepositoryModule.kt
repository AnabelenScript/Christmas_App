package com.example.deseos_navideos.features.usuarios.data.di

import com.example.deseos_navideos.features.usuarios.data.repository.UsersRepositoryImpl
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersRepositoryModule {
    @Binds
    abstract fun bindsUsersRepository(
        usersRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository
}