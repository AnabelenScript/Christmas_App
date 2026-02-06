package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateGoodness_UseCase
import com.example.deseos_navideos.features.usuarios.presentation.screens.KidsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KidsViewModel (
    private val getkidsUsecase: GetKids_UseCase,
    private val updategoodnessUsecase: UpdateGoodness_UseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(KidsUiState())
    val uiState = _uiState.asStateFlow()

    val user_id = 1
    val role = "santa"

    init {
        getKids()
    }

    fun getKids(){
        _uiState.update { it.copy(isLoading = true) }

        if (role != "santa") return

        viewModelScope.launch {
            val r = getkidsUsecase(user_id, role)
            _uiState.update { currentState ->
                r.fold(
                    onSuccess = {list ->
                        currentState.copy(isLoading = false, kids = list)
                    },
                    onFailure = {error ->
                        currentState.copy(isLoading = false, errorMessage = error.message)
                    }
                )
            }
        }
    }

    fun updateGoodness(id: Int){
        if (role != "santa") return
        viewModelScope.launch {
            updategoodnessUsecase(id, user_id, role)
            getKids()
        }
    }
}