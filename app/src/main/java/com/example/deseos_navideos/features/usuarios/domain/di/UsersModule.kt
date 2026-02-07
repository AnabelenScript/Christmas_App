package com.example.deseos_navideos.features.usuarios.domain.di

import com.example.deseos_navideos.core.di.AppContainer
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.usuarios.domain.usecases.DeleteUser_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetOneUser_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateGoodness_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateUser_UseCase
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.ConfigUserViewModelFactory
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModelFactory
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase

class UsersModule(
    private val appContainer: AppContainer
) {
    private fun provideGetKidsUseCase(): GetKids_UseCase {
        return GetKids_UseCase(appContainer.usersRepository)
    }

    private fun provideGetOneUserUseCase(): GetOneUser_UseCase {
        return GetOneUser_UseCase(appContainer.usersRepository)
    }

    private fun provideUpdateUserUseCase(): UpdateUser_UseCase {
        return UpdateUser_UseCase(appContainer.usersRepository)
    }

    private fun provideUpdateGoodnessUseCase(): UpdateGoodness_UseCase {
        return UpdateGoodness_UseCase(appContainer.usersRepository)
    }

    private fun provideDeleteUserUseCase(): DeleteUser_UseCase {
        return DeleteUser_UseCase(appContainer.usersRepository)
    }

    private fun provideGetWishesUseCase(): GetWishesUseCase {
        return GetWishesUseCase(appContainer.wishesRepository)
    }

    private fun provideDataStorage(): DataStorage {
        return appContainer.dataStorage
    }

    fun provideKidsViewModelFactory(): KidsViewModelFactory {
        return KidsViewModelFactory(
            getKidsUseCase = provideGetKidsUseCase(),

            getWishesUseCase = provideGetWishesUseCase(),
            dataStorage = provideDataStorage()
        )
    }

    fun provideConfigUserViewModelFactory(): ConfigUserViewModelFactory {
        return ConfigUserViewModelFactory(
            provideGetOneUserUseCase(),
            provideUpdateUserUseCase(),
            provideDeleteUserUseCase()
        )
    }
}
