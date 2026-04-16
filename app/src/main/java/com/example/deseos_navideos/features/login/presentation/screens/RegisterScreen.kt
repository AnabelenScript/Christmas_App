package com.example.deseos_navideos.features.login.presentation.screens

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
import com.example.deseos_navideos.features.login.presentation.components.RoleSelector
import com.example.deseos_navideos.features.login.presentation.components.HeaderCard

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
    var showFamilyCodeInput by remember { mutableStateOf(false) }

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
                        "Crea tu cuenta",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    
                    Text(
                        text = "Únete para compartir tu magia navideña",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    RoleSelector(
                        selectedRole = role,
                        onRoleChange = { 
                            viewModel.onRoleChange(it)
                            if (it == "child") showFamilyCodeInput = false
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    NavideñoTextField(
                        value = username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        label = "Usuario",
                        placeholder = "Tu nombre de usuario"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NavideñoTextField(
                        value = age,
                        onValueChange = { viewModel.onAgeChange(it) },
                        label = "Edad",
                        placeholder = "Ej. 10"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NavideñoTextField(
                        value = country,
                        onValueChange = { viewModel.onCountryChange(it) },
                        label = "País",
                        placeholder = "Tu país"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    NavideñoTextField(
                        value = password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = "Contraseña",
                        placeholder = "••••••••"
                    )

                    if (role == "child") {
                        Spacer(modifier = Modifier.height(12.dp))
                        NavideñoTextField(
                            value = familyCode,
                            onValueChange = { viewModel.onFamilyCodeChange(it) },
                            label = "Código de Santa",
                            placeholder = "Ingresa el código"
                        )
                    } else if (role == "parent") {
                        Spacer(modifier = Modifier.height(12.dp))
                        if (!showFamilyCodeInput) {
                            TextButton(onClick = { showFamilyCodeInput = true }) {
                                Text(
                                    "Ya tengo un código de familia",
                                    color = Color(0xFF2E7D32),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            NavideñoTextField(
                                value = familyCode,
                                onValueChange = { viewModel.onFamilyCodeChange(it) },
                                label = "Código de Familia",
                                placeholder = "Ingresa el código"
                            )
                        }
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
                        normalText = "¿Ya tienes cuenta? ",
                        linkText = "Inicia sesión",
                        navigateTo = "login",
                        navController = navController
                    )
                }
            }
        }
    }
}
