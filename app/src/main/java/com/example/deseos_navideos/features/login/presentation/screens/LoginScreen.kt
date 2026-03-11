package com.example.deseos_navideos.features.login.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.deseos_navideos.R
import com.example.deseos_navideos.features.login.presentation.components.AuthLink
import com.example.deseos_navideos.features.login.presentation.components.NavideñoButton
import com.example.deseos_navideos.features.login.presentation.components.NavideñoTextField
import com.example.deseos_navideos.features.login.presentation.viewmodel.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.deseos_navideos.features.login.presentation.components.NavideñoModal
import com.example.deseos_navideos.core.storage.DataStorage

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var showErrorModal by remember { mutableStateOf(false) }
    if (uiState.errorMessage != null && showErrorModal) {
        NavideñoModal(
            title = "Error de inicio de sesión",
            message = uiState.errorMessage ?: "Error desconocido",
            onConfirm = { showErrorModal = false },
            onDismiss = { showErrorModal = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.santa_icon),
            contentDescription = "Santa Claus",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bienvenido de vuelta",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
        Text(
            text = "Ingresa para enviar tus deseos a Santa",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))
        NavideñoTextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = "Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavideñoTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Contraseña"
        )

        Spacer(modifier = Modifier.height(24.dp))
        NavideñoButton(
            text = "Iniciar Sesión",
            onClick = {
                viewModel.login()
                showErrorModal = true
            },
            modifier = Modifier.fillMaxWidth()
        )

        LaunchedEffect(uiState) {
            if (!uiState.isLoading && uiState.errorMessage == null && uiState.user != null) {
                val role = uiState.user?.role ?: "child"
                if (role == "parent") {
                    navController.navigate("users")
                } else if (role == "child") {
                    navController.navigate("wishes")
                } else {
                    Log.d("LoginScreen", "Rol desconocido: $role")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        AuthLink(
            normalText = "¿No tienes una cuenta?",
            linkText = "Regístrate aquí",
            navigateTo = "register",
            navController = navController
        )
    }
}
