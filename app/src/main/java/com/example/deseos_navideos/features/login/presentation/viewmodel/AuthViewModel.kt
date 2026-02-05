package com.example.deseos_navideos.features.login.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.features.login.domain.usecases.LoginUseCase
import com.example.deseos_navideos.features.login.domain.usecases.RegisterUseCase
import com.example.deseos_navideos.features.login.presentation.screens.AuthUiState
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(AuthUiState())
    val uiState: LiveData<AuthUiState> = _uiState

    // variables locales para inputs
    var username: String = ""
    var password: String = ""
    var age: String = ""
    var country: String = ""

    fun login() {
        _uiState.value = _uiState.value?.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val user = loginUseCase(username, password)
                _uiState.value = _uiState.value?.copy(isLoading = false, user = user)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun register() {
        _uiState.value = _uiState.value?.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val id = registerUseCase(username, age.toIntOrNull() ?: 0, country, password)
                _uiState.value = _uiState.value?.copy(isLoading = false, registeredUserId = id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}
