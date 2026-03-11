package com.example.deseos_navideos.features.login.presentation.screens

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
import com.example.deseos_navideos.features.login.presentation.components.RoleSelector

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    val uiState by viewModel.uiState.collectAsState()

    val username by viewModel.username.collectAsState()
    val age by viewModel.age.collectAsState()
    val country by viewModel.country.collectAsState()
    val password by viewModel.password.collectAsState()
    val role by viewModel.role.collectAsState()
    val familyCode by viewModel.familyCode.collectAsState()

    var showErrorModal by remember { mutableStateOf(false) }

    if (uiState.errorMessage != null && showErrorModal) {

        NavideñoModal(
            title = "Error de registro",
            message = uiState.errorMessage ?: "Error desconocido",
            onConfirm = { showErrorModal = false },
            onDismiss = { showErrorModal = false }
        )
    }

    LaunchedEffect(uiState.isSuccess) {

        if (uiState.isSuccess) {
            navController.navigate("login")
        }
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
            contentDescription = "Santa",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Crea tu cuenta",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        RoleSelector (
            selectedRole = role,
            onRoleChange = { viewModel.onRoleChange(it) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavideñoTextField(
            value = username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = "Usuario"
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavideñoTextField(
            value = age,
            onValueChange = { viewModel.onAgeChange(it) },
            label = "Edad"
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavideñoTextField(
            value = country,
            onValueChange = { viewModel.onCountryChange(it) },
            label = "País"
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavideñoTextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Contraseña"
        )

        if (role == "child") {

            Spacer(modifier = Modifier.height(16.dp))

            NavideñoTextField(
                value = familyCode,
                onValueChange = { viewModel.onFamilyCodeChange(it) },
                label = "Código familiar"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        NavideñoButton(
            text = "Registrarse",
            onClick = {
                viewModel.register()
                showErrorModal = true
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        AuthLink(
            normalText = "¿Ya tienes cuenta?",
            linkText = "Inicia sesión",
            navigateTo = "login",
            navController = navController
        )
    }
}