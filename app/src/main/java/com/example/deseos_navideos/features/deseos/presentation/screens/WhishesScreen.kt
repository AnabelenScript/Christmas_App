package com.example.deseos_navideos.features.deseos.presentation.screens

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel
import com.example.deseos_navideos.features.deseos.presentation.components.WishCard
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.core.theme.bodyFontFamily
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.login.presentation.components.NavideñoTextField

@Composable
fun WishesScreen(
    viewModel: WishesViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val username by UserSession.username.collectAsState()
    val context = LocalContext.current

    val totalWishes = uiState.wishes.size
    val completedWishes = uiState.wishes.count { it.state == "completed" || it.state == "cumplido" }
    val pendingWishes = totalWishes - completedWishes

    var selectedWishId by remember { mutableStateOf<Int?>(null) }
    var showDetailDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showAudioDialog by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Encontrar el deseo seleccionado en la lista actual para que se actualice la UI
    val selectedWish = uiState.wishes.find { it.id == selectedWishId }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                capturedImageUri = tempImageUri
            }
        }
    )

    // Permission Launcher para Cámara
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

    // Permission Launcher para Micrófono
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
                // Botón para Audio
                FloatingActionButton(
                    onClick = { micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO) },
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Mic, contentDescription = "Subir Audio")
                }

                // Botón para Agregar Deseo
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
                        containerColor = Color(0xFFF1F3F4),
                        contentColor = Color.Gray
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

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡Deseo agregado!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tu deseo ha sido enviado a Santa correctamente.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = bodyFontFamily,
                        color = Color.Gray
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("¡Genial!", fontFamily = bodyFontFamily)
                }
            }
        }
    }
}

@Composable
fun AudioRecordDialog(
    viewModel: WishesViewModel,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Dialog(onDismissRequest = if (uiState.isRecording) ({}) else onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mensaje de Voz para Santa",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = if (uiState.isRecording) Color(0xFFFFEBEE) else Color(0xFFF1F3F4),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = if (uiState.isRecording) Icons.Default.Mic else Icons.Default.MicNone,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = if (uiState.isRecording) Color(0xFFD32F2F) else Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = String.format("%02d:%02d", uiState.recordingDuration / 60, uiState.recordingDuration % 60),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = bodyFontFamily
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (uiState.isRecording) "Grabando..." else "Listo para grabar",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (uiState.isRecording) Color(0xFFD32F2F) else Color.Gray,
                        fontFamily = bodyFontFamily
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (!uiState.isRecording && uiState.recordingDuration == 0) {
                        Button(
                            onClick = { viewModel.startRecording(context.cacheDir) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Grabar", fontFamily = bodyFontFamily)
                        }
                    } else if (uiState.isRecording) {
                        Button(
                            onClick = { viewModel.stopRecording() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Detener", fontFamily = bodyFontFamily)
                        }
                    } else {
                        Button(
                            onClick = { 
                                viewModel.uploadAudio()
                                onDismiss()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text("Subir Audio", fontFamily = bodyFontFamily)
                            }
                        }
                        
                        OutlinedButton(
                            onClick = { viewModel.startRecording(context.cacheDir) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Reintentar", fontFamily = bodyFontFamily)
                        }
                    }
                }

                if (!uiState.isRecording) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Cancelar", color = Color.Gray, fontFamily = bodyFontFamily)
                    }
                }
            }
        }
    }
}

@Composable
fun AddWishDialog(
    viewModel: WishesViewModel,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nuevo Deseo",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                NavideñoTextField(
                    value = uiState.newWish,
                    onValueChange = { viewModel.onNewWishChange(it) },
                    label = "Descripción",
                    placeholder = "¿Qué le quieres pedir a Santa?"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        viewModel.addWish()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Agregar a mi carta", fontFamily = bodyFontFamily)
                }

                TextButton(onClick = onDismiss) {
                    Text("Cancelar", color = Color.Gray, fontFamily = bodyFontFamily)
                }
            }
        }
    }
}

@Composable
fun WishDetailDialog(
    wish: Wish,
    capturedImageUri: Uri?,
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onUpdateWish: (String, Uri?) -> Unit
) {
    var editedDescription by remember { mutableStateOf(wish.wish) }
    val currentPhoto = capturedImageUri ?: wish.photoUrl

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Detalle del Deseo",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = bodyFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                NavideñoTextField(
                    value = editedDescription,
                    onValueChange = { editedDescription = it },
                    label = "Descripción",
                    placeholder = "Describe tu deseo"
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (currentPhoto != null) {
                    AsyncImage(
                        model = currentPhoto,
                        contentDescription = "Foto del deseo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF1F3F4)),
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onTakePhoto) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (wish.photoUrl != null) "Cambiar Foto" else "Tomar otra",
                            fontFamily = bodyFontFamily,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    Button(
                        onClick = onTakePhoto,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tomar Foto", fontFamily = bodyFontFamily)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onUpdateWish(editedDescription, capturedImageUri)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Actualizar Deseo", fontFamily = bodyFontFamily)
                }

                TextButton(onClick = onDismiss) {
                    Text("Cerrar", color = Color.Gray, fontFamily = bodyFontFamily)
                }
            }
        }
    }
}

@Composable
fun StatCard(
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
