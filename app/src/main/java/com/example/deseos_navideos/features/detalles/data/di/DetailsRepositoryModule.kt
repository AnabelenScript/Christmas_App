package com.example.deseos_navideos.features.detalles.data.di

import com.example.deseos_navideos.features.detalles.data.repository.DetailsRepositoryImpl
import com.example.deseos_navideos.features.detalles.domain.repositories.DetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(
        detailsRepositoryImpl: DetailsRepositoryImpl
    ): DetailsRepository
}
