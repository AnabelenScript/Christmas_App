package com.example.deseos_navideos.features.deseos.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel

@Composable
fun AudioRecordDialog(
    viewModel: WishesViewModel,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
