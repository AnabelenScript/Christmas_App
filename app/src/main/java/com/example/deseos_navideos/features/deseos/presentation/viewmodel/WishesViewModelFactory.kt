package com.example.deseos_navideos.features.deseos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.deseos.domain.usecases.CreateWishUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.UpdateWishesUseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.DeleteWishUseCase

class WishesViewModelFactory(
    private val createWishUseCase: CreateWishUseCase,
    private val getWishesUseCase: GetWishesUseCase,
    private val updateWishesUseCase: UpdateWishesUseCase,
    private val deleteWishUseCase: DeleteWishUseCase,
    private val dataStorage: DataStorage,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WishesViewModel::class.java)) {
            return WishesViewModel(
                createWishUseCase,
                getWishesUseCase,
                updateWishesUseCase,
                deleteWishUseCase,
                dataStorage
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
