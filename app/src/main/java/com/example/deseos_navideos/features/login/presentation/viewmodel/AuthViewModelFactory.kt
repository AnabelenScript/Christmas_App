package com.example.deseos_navideos.features.login.presentation.viewmodel
import com.example.deseos_navideos.features.login.domain.usecases.RegisterUseCase
import com.example.deseos_navideos.features.login.domain.usecases.LoginUseCase
import com.example.deseos_navideos.features.login.domain.usecases.LogoutUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthViewModelFactory (
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(loginUseCase, registerUseCase, logoutUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
