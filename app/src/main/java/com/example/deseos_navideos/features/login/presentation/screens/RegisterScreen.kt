package com.example.deseos_navideos.features.login.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deseos_navideos.features.login.presentation.components.NavideñoButton
import com.example.deseos_navideos.features.login.presentation.components.NavideñoTextField
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel
import com.example.deseos_navideos.features.login.presentation.screens.AuthUiState

@Composable
fun RegisterScreen(viewModel: AuthViewModel) {
    val uiState by viewModel.uiState.observeAsState(AuthUiState())

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Register", style = MaterialTheme.typography.titleLarge)

        NavideñoTextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = "Username"
        )

        NavideñoTextField(
            value = viewModel.age,
            onValueChange = { viewModel.age = it },
            label = "Age"
        )

        NavideñoTextField(
            value = viewModel.country,
            onValueChange = { viewModel.country = it },
            label = "Country"
        )

        NavideñoTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = "Password"
        )

        NavideñoButton(
            text = "Register",
            onClick = { viewModel.register() },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading -> CircularProgressIndicator()
            uiState.registeredUserId != null -> Text("Usuario registrado con ID: ${uiState.registeredUserId} 🎁")
            uiState.errorMessage != null -> Text("Error: ${uiState.errorMessage}", color = MaterialTheme.colorScheme.error)
        }
    }
}
