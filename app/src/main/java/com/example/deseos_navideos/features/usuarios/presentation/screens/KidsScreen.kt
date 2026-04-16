package com.example.deseos_navideos.features.usuarios.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModel

@Composable
fun KidsScreen(
    navController: NavController,
    onKidClick: (Int) -> Unit,
    onLogout: () -> Unit,
    viewModel: KidsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val username by UserSession.username.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    
    LaunchedEffect(Unit) {
        viewModel.loadKids()
    }

    val allWishes = uiState.kids.flatMap { it.wishes }
    val totalWishes = allWishes.size
    val completedWishes = allWishes.count {
        it.state.lowercase() in listOf("completed", "cumplido", "comprado")
    }
    val pendingWishes = totalWishes - completedWishes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "panel de familia",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray,
                        fontFamily = bodyFontFamily
                    )
                )
                Text(
                    text = username ?: "Papá/Mamá",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = bodyFontFamily,
                        fontSize = 36.sp
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { navController.navigate("notifications") },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF2E7D32),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificaciones",
                        modifier = Modifier.size(24.dp)
                    )
                }

                Button(
                    onClick = {
                        UserSession.logout()
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("salir", fontFamily = bodyFontFamily)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(value = totalWishes.toString(), label = "total", modifier = Modifier.weight(1f))
            StatCard(
                value = completedWishes.toString(),
                label = "cumplidos",
                modifier = Modifier.weight(1f),
                valueColor = Color(0xFF2E7D32)
            )
            StatCard(
                value = pendingWishes.toString(),
                label = "pendientes",
                modifier = Modifier.weight(1f),
                valueColor = Color(0xFFD32F2F)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        uiState.familyCode?.let { code ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        clipboardManager.setText(AnnotatedString(code))
                        Toast.makeText(context, "Código copiado", Toast.LENGTH_SHORT).show()
                    },
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF9A0000)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Código de Santa",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color(0xFFFFFFFF),
                                fontFamily = bodyFontFamily
                            )
                        )
                        Text(
                            text = code,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFFFFF),
                                fontFamily = bodyFontFamily
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar",
                        tint = Color(0xFFFFFFFF)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 4.dp, color = Color(0xFFF1F3F4))
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.isLoading && uiState.kids.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFD32F2F))
                }
            }
            uiState.errorMessage != null && uiState.kids.isEmpty() -> {
                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = bodyFontFamily
                )
            }
            uiState.kids.isEmpty() -> {
                Text(
                    "No hay niños registrados.",
                    fontFamily = bodyFontFamily,
                    color = Color.Gray
                )
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(uiState.kids) { kid ->
                        KidsCard(
                            kid = kid,
                            onDetailsClick = { onKidClick(kid.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Color.Black
) {
    Surface(
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF1F3F4)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = valueColor,
                    fontFamily = bodyFontFamily
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray,
                    fontFamily = bodyFontFamily
                )
            )
        }
    }
}
