package com.example.deseos_navideos.features.login.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.features.login.domain.usecases.LoginUseCase
import com.example.deseos_navideos.features.login.domain.usecases.RegisterUseCase
import com.example.deseos_navideos.features.login.domain.usecases.LogoutUseCase
import com.example.deseos_navideos.features.login.presentation.screens.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _age = MutableStateFlow("")
    val age = _age.asStateFlow()

    private val _country = MutableStateFlow("")
    val country = _country.asStateFlow()

    private val _role = MutableStateFlow("child")
    val role = _role.asStateFlow()

    private val _familyCode = MutableStateFlow("")
    val familyCode = _familyCode.asStateFlow()

    fun onUsernameChange(v: String) { _username.value = v }
    fun onPasswordChange(v: String) { _password.value = v }
    fun onAgeChange(v: String) { _age.value = v }
    fun onCountryChange(v: String) { _country.value = v }
    fun onRoleChange(v: String) { _role.value = v }
    fun onFamilyCodeChange(v: String) { _familyCode.value = v }

    fun login() {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {

            val result = loginUseCase(
                _username.value,
                _password.value
            )

            result.fold(

                onSuccess = { user ->

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = user
                    )

                },

                onFailure = { e ->

                    val message = if (e is HttpException) {
                        when (e.code()) {
                            401 -> "Credenciales incorrectas"
                            404 -> "Usuario no encontrado"
                            else -> "Error del servidor"
                        }
                    } else {
                        e.message ?: "Error desconocido"
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
            )
        }
    }

    fun register() {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        viewModelScope.launch {

            val result = registerUseCase(
                username = _username.value,
                age = _age.value.toIntOrNull() ?: 0,
                country = _country.value,
                password = _password.value,
                role = _role.value,
                familyCode =
                    if (_role.value == "parent") null
                    else _familyCode.value
            )

            result.fold(

                onSuccess = { id ->

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        registeredUserId = id,
                        isSuccess = true
                    )

                },

                onFailure = { e ->

                    val message = if (e is HttpException) {
                        when (e.code()) {
                            409 -> "Usuario ya registrado"
                            400 -> "Datos inválidos"
                            else -> "Error del servidor"
                        }
                    } else {
                        e.message ?: "Error desconocido"
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = message
                    )
                }
            )
        }
    }

    fun logout() {
        logoutUseCase()
        _uiState.value = AuthUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}