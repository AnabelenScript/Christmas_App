package com.example.deseos_navideos.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deseos_navideos.features.login.presentation.screens.RegisterScreen
import com.example.deseos_navideos.features.login.presentation.screens.LoginScreen
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel
import com.example.deseos_navideos.features.deseos.presentation.screens.WishesScreen
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel
import com.example.deseos_navideos.features.usuarios.presentation.screens.KidsScreen
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModel
import com.example.deseos_navideos.core.storage.DataStorage

@Composable
fun NavigationWrapper(
    authViewModel: AuthViewModel,
    wishesViewModel: WishesViewModel,
    userViewModel: KidsViewModel,
    dataStorage: DataStorage,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(viewModel = authViewModel, navController = navController, dataStorage = dataStorage)
        }
        composable("register") {
            RegisterScreen(authViewModel, navController)
        }
        composable("wishes") {
            WishesScreen(wishesViewModel)
        }
        composable("users") {
            KidsScreen(userViewModel)
        }

    }
}
