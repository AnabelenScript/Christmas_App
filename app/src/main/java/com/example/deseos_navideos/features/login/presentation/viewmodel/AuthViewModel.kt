package com.example.deseos_navideos.features.login.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deseos_navideos.features.login.domain.usecases.LoginUseCase
import com.example.deseos_navideos.features.login.domain.usecases.RegisterUseCase
import com.example.deseos_navideos.features.login.domain.usecases.LogoutUseCase
import com.example.deseos_navideos.features.login.presentation.screens.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _age = MutableStateFlow("")
    val age: StateFlow<String> = _age
    private val _country = MutableStateFlow("")
    val country: StateFlow<String> = _country
    private val _role = MutableStateFlow("child")
    val role: StateFlow<String> = _role
    private val _familyCode = MutableStateFlow("")
    val familyCode: StateFlow<String> = _familyCode

    fun onUsernameChange(newValue: String) { _username.value = newValue }
    fun onPasswordChange(newValue: String) { _password.value = newValue }
    fun onAgeChange(newValue: String) { _age.value = newValue }
    fun onCountryChange(newValue: String) { _country.value = newValue }
    fun onRoleChange(newValue: String) { _role.value = newValue }
    fun onFamilyCodeChange(newValue: String) { _familyCode.value = newValue }

    fun login() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val userValue = _username.value
                val passValue = _password.value

                Log.d("AuthViewModel", "Login called with username=$userValue, password=$passValue")

                val user = loginUseCase(userValue, passValue)
                _uiState.value = _uiState.value.copy(isLoading = false, user = user)
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    400 -> "Solicitud inválida"
                    401 -> "Credenciales incorrectas"
                    404 -> "Usuario no encontrado"
                    500 -> "Error interno del servidor"
                    else -> "Error inesperado (${e.code()})"
                }
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = message)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    fun register() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val id = registerUseCase(
                    _username.value,
                    _age.value.toIntOrNull() ?: 0,
                    _country.value,
                    _password.value,
                    _role.value,
                    if (_role.value == "parent") null else _familyCode.value
                )
                _uiState.value = _uiState.value.copy(isLoading = false, registeredUserId = id)
            } catch (e: HttpException) {
                val message = when (e.code()) {
                    400 -> "Datos inválidos"
                    409 -> "Usuario ya registrado"
                    500 -> "Error interno del servidor"
                    else -> "Error inesperado (${e.code()})"
                }
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = message)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
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
