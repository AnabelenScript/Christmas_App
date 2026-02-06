package com.example.deseos_navideos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.deseos_navideos.core.di.AppContainer
import com.example.deseos_navideos.core.navigation.NavigationWrapper
import com.example.deseos_navideos.features.login.domain.usecases.RegisterUseCase
import com.example.deseos_navideos.features.login.domain.usecases.LoginUseCase
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)

        val registerUseCase = RegisterUseCase(appContainer.authRepository)
        val loginUseCase = LoginUseCase(appContainer.authRepository)

        val factory = AuthViewModelFactory(loginUseCase, registerUseCase)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavigationWrapper(
                    viewModel = authViewModel,
                    modifier = Modifier.padding(innerPadding) )
            }
        }
    }
}