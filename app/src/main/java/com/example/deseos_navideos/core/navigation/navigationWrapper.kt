package com.example.deseos_navideos.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deseos_navideos.features.login.presentation.screens.RegisterScreen
import com.example.deseos_navideos.features.login.presentation.screens.LoginScreen
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel

@Composable
fun NavigationWrapper(viewModel: AuthViewModel, modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(viewModel, navController)
        }
        composable("register") {
            RegisterScreen(viewModel, navController)
        }
    }
}
