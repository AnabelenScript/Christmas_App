package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deseos_navideos.features.usuarios.domain.usecases.DeleteUser_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetOneUser_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateUser_UseCase

class ConfigUserViewModelFactory(
    private val getoneuserUsecase: GetOneUser_UseCase,
    private val updateuserUsecase: UpdateUser_UseCase,
    private val deleteuserUsecase: DeleteUser_UseCase,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigUserViewModel::class.java)) {
            return ConfigUserViewModel(
                getoneuserUsecase,
                updateuserUsecase,
                deleteuserUsecase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}