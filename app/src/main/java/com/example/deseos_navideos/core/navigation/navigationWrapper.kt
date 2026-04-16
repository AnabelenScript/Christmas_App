package com.example.deseos_navideos.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deseos_navideos.features.login.presentation.screens.RegisterScreen
import com.example.deseos_navideos.features.login.presentation.screens.LoginScreen
import com.example.deseos_navideos.features.deseos.presentation.screens.WishesScreen
import com.example.deseos_navideos.features.usuarios.presentation.screens.KidsScreen
import com.example.deseos_navideos.features.detalles.presentation.screens.KidDetailsScreen
import com.example.deseos_navideos.features.notificaciones.presentation.screens.NotificationsScreen

@Composable
fun NavigationWrapper(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("wishes") {
            WishesScreen(navController = navController)
        }
        composable("users") {
            KidsScreen(
                navController = navController,
                onKidClick = { kidId ->
                    navController.navigate("kid_details/$kidId")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(
            route = "kid_details/{kidId}",
            arguments = listOf(navArgument("kidId") { type = NavType.IntType })
        ) {
            KidDetailsScreen(navController = navController)
        }
        composable("notifications") {
            NotificationsScreen(navController = navController)
        }
    }
}
