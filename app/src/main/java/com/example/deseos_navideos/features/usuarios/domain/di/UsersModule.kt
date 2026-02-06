package com.example.deseos_navideos.features.usuarios.domain.di

import com.example.deseos_navideos.core.di.AppContainer
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateGoodness_UseCase
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModelFactory

class UsersModule (
    private val appContainer: AppContainer
) {
    private fun provideGetKidsUseCase(): GetKids_UseCase {
        return GetKids_UseCase(appContainer.usersRepository)
    }

    private fun provideUpdateGoodnesUseCase(): UpdateGoodness_UseCase {
        return UpdateGoodness_UseCase(appContainer.usersRepository)
    }

    fun provideKidsViewModelFactory(): KidsViewModelFactory {
        return KidsViewModelFactory(
            provideGetKidsUseCase(),
            provideUpdateGoodnesUseCase()
        )
    }
}