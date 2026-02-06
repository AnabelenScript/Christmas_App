package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateGoodness_UseCase

class KidsViewModelFactory(
    private val getkidsUsecase: GetKids_UseCase,
    private val updategoodnessUsecase: UpdateGoodness_UseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KidsViewModel::class.java)) {
            return KidsViewModel(
                getkidsUsecase,
                updategoodnessUsecase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}