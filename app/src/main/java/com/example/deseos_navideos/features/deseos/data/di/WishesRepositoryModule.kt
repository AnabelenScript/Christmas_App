package com.example.deseos_navideos.features.deseos.data.di

import com.example.deseos_navideos.features.deseos.data.repositories.WishesRepositoryImpl
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WishesRepositoryModule {
    @Binds
    abstract fun bindWishesRepository(
        wishesRepositoryImpl: WishesRepositoryImpl
    ): WishesRepository
}