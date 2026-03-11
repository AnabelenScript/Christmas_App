package com.example.deseos_navideos.features.usuarios.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.features.usuarios.presentation.screens.ConfigUserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfigUserViewModel (
    // private val getoneuserUsecase: GetOneUser_UseCase,
    // private val updateuserUsecase: UpdateUser_UseCase,
    // private val deleteuserUsecase: DeleteUser_UseCase,
): ViewModel() {
    /*
    private val _uiState = MutableStateFlow(ConfigUserUiState(false, null, null))
    val uiState = _uiState.asStateFlow()

    val user_id = 1
    val role = "santa"

    // Inputs
    val username = ""
    val age = 0
    val country = ""
    val password = ""

    init {
        loadPersonalInfo()
    }

    fun loadPersonalInfo(){
        if(role == "santa") return
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val r = getoneuserUsecase(user_id, user_id, role)
            _uiState.update { currentState ->
                r.fold(
                    onSuccess = {user ->
                        currentState.copy(isLoading = false, userInfo = user)
                    },
                    onFailure = {error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }

    fun setPersonalInfo() {
        if(role == "santa") return
        if(username == "" && age == 0 && country == "" && password == "")
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            updateuserUsecase(user_id, username, age, country, password, user_id, role)
            loadPersonalInfo()
        }
    }

    fun deleteUser() {
        if (role == "santa") return
        viewModelScope.launch {
            deleteuserUsecase(user_id, user_id, role)
            // Agregar enrutado para que vuelva al login
        }
    }
    */
}