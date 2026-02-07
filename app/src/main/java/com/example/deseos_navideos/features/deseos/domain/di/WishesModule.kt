package com.example.deseos_navideos.features.deseos.domain.di

import com.example.deseos_navideos.core.di.AppContainer
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWishUseCase
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModelFactory

class WishesModule(
    private val appContainer: AppContainer,
) {
    private fun provideCreateWish(): CreateWishUseCase {
        return CreateWishUseCase(appContainer.wishesRepository)
    }

    private fun provideGetWishes(): GetWishesUseCase {
        return GetWishesUseCase(appContainer.wishesRepository)
    }

    private fun provideUpdateWish(): UpdateWishesUseCase {
        return UpdateWishesUseCase(appContainer.wishesRepository)
    }

    private fun provideDeleteWish(): DeleteWishUseCase {
        return DeleteWishUseCase(appContainer.wishesRepository)
    }

    private fun provideDataStorage(): DataStorage {
        return appContainer.dataStorage
    }

    fun provideWishesViewModelFactory(): WishesViewModelFactory {
        return WishesViewModelFactory(
            createWishUseCase = provideCreateWish(),
            getWishesUseCase = provideGetWishes(),
            updateWishesUseCase = provideUpdateWish(),
            deleteWishUseCase = provideDeleteWish(),
            dataStorage = provideDataStorage()
        )
    }
}
