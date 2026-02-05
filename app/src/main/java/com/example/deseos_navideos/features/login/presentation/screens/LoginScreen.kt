package com.example.deseos_navideos.features.login.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deseos_navideos.features.login.presentation.components.NavideñoButton
import com.example.deseos_navideos.features.login.presentation.components.NavideñoTextField
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel) {
    val uiState by viewModel.uiState.observeAsState(AuthUiState())
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Login", style = MaterialTheme.typography.titleLarge)
        NavideñoTextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = "Username"
        )
        NavideñoTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = "Password"
        )
        NavideñoButton(text = "Login", onClick = { viewModel.login() })

        if (uiState.isLoading) CircularProgressIndicator()

        uiState.user?.let {
            Text("Bienvenido ${it.username} 🎅")
        }

        uiState.errorMessage?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
    }
}
