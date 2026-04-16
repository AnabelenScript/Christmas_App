package com.example.deseos_navideos.features.deseos.presentation.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.login.presentation.components.NavideñoTextField

@Composable
fun WishDetailDialog(
    wish: Wish,
    capturedImageUri: Uri?,
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onUpdateWish: (String, Uri?) -> Unit
) {
    var editedDescription by remember { mutableStateOf(wish.wish) }
    
    // Prioridad: 
    // 1. Imagen recién capturada (Uri)
    // 2. Imagen local guardada en DB (Path de la galería)
    // 3. Imagen remota (URL del servidor)
    val currentPhoto = capturedImageUri ?: wish.localFilePath ?: wish.photoUrl

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
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Botón de Tomar Foto: Fondo Rojo, Letra Blanca
                Button(
                    onClick = onTakePhoto,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentPhoto != null) "Cambiar Foto" else "Tomar Foto",
                        fontFamily = bodyFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botón de Actualizar Deseo: Fondo Verde, Letra Blanca
                Button(
                    onClick = {
                        onUpdateWish(editedDescription, capturedImageUri)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32),
                        contentColor = Color.White
                    ),
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
