package com.example.deseos_navideos.features.deseos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWish_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWish_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishes_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishes_UseCase

class WishesViewModelFactory (
    private val createwishUsecase: CreateWish_UseCase,
    private val getwishesUsecase: GetWishes_UseCase,
    private val updatewishesUsecase: UpdateWishes_UseCase,
    private val deletewishUsecase: DeleteWish_UseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishesViewModel::class.java)) {
            return WishesViewModel(
                createwishUsecase,
                getwishesUsecase,
                updatewishesUsecase,
                deletewishUsecase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}