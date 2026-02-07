package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deseos_navideos.core.storage.DataStorage
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.deseos.domain.usecases.GetWishesUseCase

class KidsViewModelFactory(
    private val getKidsUseCase: GetKids_UseCase,
    private val getWishesUseCase: GetWishesUseCase,
    private val dataStorage: DataStorage
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KidsViewModel::class.java)) {
            return KidsViewModel(
                getKidsUseCase,
                getWishesUseCase,
                dataStorage
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
