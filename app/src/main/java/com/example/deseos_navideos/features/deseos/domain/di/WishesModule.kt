package com.example.deseos_navideos.features.deseos.domain.di

import com.example.deseos_navideos.core.di.AppContainer
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWish_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWish_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishes_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishes_UseCase
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModelFactory

class WishesModule (
    private val appContainer: AppContainer,
) {
    private fun provideCreateWish(): CreateWish_UseCase {
        return CreateWish_UseCase(appContainer.wishesRepository)
    }

    private fun provideGetWishes(): GetWishes_UseCase {
        return GetWishes_UseCase(appContainer.wishesRepository)
    }

    private fun provideUpdateWish(): UpdateWishes_UseCase {
        return UpdateWishes_UseCase(appContainer.wishesRepository)
    }

    private fun provideDeleteWish(): DeleteWish_UseCase {
        return DeleteWish_UseCase(appContainer.wishesRepository)
    }

    fun provideWishesViewModelFactory(): WishesViewModelFactory {
        return WishesViewModelFactory(
            provideCreateWish(),
            provideGetWishes(),
            provideUpdateWish(),
            provideDeleteWish(),
        )
    }
}