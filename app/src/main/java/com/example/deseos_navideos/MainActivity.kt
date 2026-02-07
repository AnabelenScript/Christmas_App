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
import com.example.deseos_navideos.core.di.AppContainer
import com.example.deseos_navideos.core.navigation.NavigationWrapper
import com.example.deseos_navideos.features.deseos.domain.usecases.*
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModelFactory
import com.example.deseos_navideos.features.login.domain.usecases.RegisterUseCase
import com.example.deseos_navideos.features.login.domain.usecases.LoginUseCase
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModelFactory
import com.example.deseos_navideos.features.usuarios.domain.usecases.GetKids_UseCase
import com.example.deseos_navideos.features.usuarios.domain.usecases.UpdateGoodness_UseCase
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModel

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer
    private lateinit var authViewModel: AuthViewModel
    private lateinit var wishesViewModel: WishesViewModel
    private lateinit var kidsViewModel: KidsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)

        val registerUseCase = RegisterUseCase(appContainer.authRepository)
        val loginUseCase = LoginUseCase(appContainer.authRepository)
        val createWishUseCase = CreateWishUseCase(appContainer.wishesRepository)
        val getWishesUseCase = GetWishesUseCase(appContainer.wishesRepository)
        val updateWishesUseCase = UpdateWishesUseCase(appContainer.wishesRepository)
        val deleteWishUseCase = DeleteWishUseCase(appContainer.wishesRepository)

        val authFactory = AuthViewModelFactory(loginUseCase, registerUseCase)
        authViewModel = ViewModelProvider(this, authFactory)[AuthViewModel::class.java]

        val wishesFactory = WishesViewModelFactory(
            createWishUseCase,
            getWishesUseCase,
            updateWishesUseCase,
            deleteWishUseCase,
            appContainer.dataStorage
        )
        wishesViewModel = ViewModelProvider(this, wishesFactory)[WishesViewModel::class.java]
        kidsViewModel = KidsViewModel(
            getKidsUseCase = GetKids_UseCase(appContainer.usersRepository),

            getWishesUseCase = getWishesUseCase,
            dataStorage = appContainer.dataStorage
        )

        enableEdgeToEdge()
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavigationWrapper(
                    authViewModel = authViewModel,
                    wishesViewModel = wishesViewModel,
                    userViewModel = kidsViewModel,
                    dataStorage = appContainer.dataStorage,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
