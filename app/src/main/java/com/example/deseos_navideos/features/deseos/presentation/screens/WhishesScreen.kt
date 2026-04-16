package com.example.deseos_navideos.features.deseos.presentation.screens

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel
import com.example.deseos_navideos.features.deseos.presentation.components.*
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily

@Composable
fun WishesScreen(
    viewModel: WishesViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val username by UserSession.username.collectAsState()
    val context = LocalContext.current

    val totalWishes = uiState.wishes.size
    val completedWishes = uiState.wishes.count { 
        it.state.lowercase() in listOf("completed", "cumplido", "comprado") 
    }
    val pendingWishes = totalWishes - completedWishes

    var selectedWishId by remember { mutableStateOf<Int?>(null) }
    var showDetailDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showAudioDialog by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    val selectedWish = uiState.wishes.find { it.id == selectedWishId }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                capturedImageUri = tempImageUri
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                val uri = viewModel.getNewImageUri()
                tempImageUri = uri
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                showAudioDialog = true
            } else {
                Toast.makeText(context, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO) },
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Subir Audio")
                }

                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Deseo")
                }
            }
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                        text = "mi carta a santa",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontFamily = bodyFontFamily
                        )
                    )
                    Text(
                        text = username ?: "Niño/a",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = bodyFontFamily,
                            fontSize = 36.sp
                        )
                    )
                }

                Button(
                    onClick = {
                        UserSession.logout()
                        navController.navigate("login") {
                            popUpTo(0)
                        }
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

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(value = totalWishes.toString(), label = "total", modifier = Modifier.weight(1f))
                StatCard(value = completedWishes.toString(), label = "cumplidos", modifier = Modifier.weight(1f), valueColor = Color(0xFF2E7D32))
                StatCard(value = pendingWishes.toString(), label = "pendientes", modifier = Modifier.weight(1f), valueColor = Color(0xFFD32F2F))
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(thickness = 4.dp, color = Color(0xFFF1F3F4))
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading && uiState.wishes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFD32F2F))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(uiState.wishes) { wish ->
                        WishCard(
                            wish = wish,
                            onDelete = { viewModel.removeWish(wish.id) },
                            onClick = {
                                selectedWishId = wish.id
                                capturedImageUri = null
                                showDetailDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddWishDialog(
            viewModel = viewModel,
            onDismiss = { showAddDialog = false }
        )
    }

    if (showAudioDialog) {
        AudioRecordDialog(
            viewModel = viewModel,
            onDismiss = { showAudioDialog = false }
        )
    }

    if (uiState.showSuccessModal) {
        SuccessDialog(
            onDismiss = { viewModel.dismissSuccessModal() }
        )
    }

    if (showDetailDialog && selectedWish != null) {
        WishDetailDialog(
            wish = selectedWish!!,
            capturedImageUri = capturedImageUri,
            onDismiss = { 
                showDetailDialog = false
                capturedImageUri = null
            },
            onTakePhoto = {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            },
            onUpdateWish = { newDescription, newPhotoUri ->
                if (newDescription != selectedWish!!.wish) {
                    viewModel.editWish(selectedWish!!.id, newDescription)
                }
                if (newPhotoUri != null) {
                    viewModel.uploadPhoto(selectedWish!!.id, newPhotoUri)
                }
                capturedImageUri = null
            }
        )
    }
}
