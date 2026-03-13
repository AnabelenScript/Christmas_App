package com.example.deseos_navideos.features.deseos.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel
import com.example.deseos_navideos.features.login.presentation.components.NavideñoTextField

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
                    placeholder = "Escribe tu deseo..."
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
