package com.example.deseos_navideos.features.login.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.deseos_navideos.features.login.presentation.components.HeaderCard

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EEF4)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.santa_icon),
                contentDescription = "Santa Claus",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderCard()
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Bienvenido de vuelta",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    
                    Text(
                        text = "Ingresa para enviar tus deseos a Santa",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    NavideñoTextField(
                        value = username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        label = "Nombre de usuario",
                        placeholder = "Tu usuario"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    NavideñoTextField(
                        value = password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = "Contraseña",
                        placeholder = "••••••••"
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    NavideñoButton(
                        text = "Iniciar Sesión",
                        onClick = {
                            viewModel.login()
                            showErrorModal = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthLink(
                        normalText = "¿No tienes cuenta? ",
                        linkText = "Regístrate aquí",
                        navigateTo = "register",
                        navController = navController
                    )
                }
            }
        }
    }

    LaunchedEffect(uiState) {
        if (!uiState.isLoading && uiState.errorMessage == null && uiState.user != null) {
            val role = uiState.user?.role ?: "child"
            if (role == "parent") {
                navController.navigate("users")
            } else if (role == "child") {
                navController.navigate("wishes")
            }
        }
    }
}
