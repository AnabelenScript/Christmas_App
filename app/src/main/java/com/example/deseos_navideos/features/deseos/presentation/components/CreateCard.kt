package com.example.deseos_navideos.features.deseos.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel


@Composable
fun AddCard(viewModel: WishesViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }

    Column {

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar deseo")
        }

        if (showDialog) {

            AlertDialog(
                onDismissRequest = { showDialog = false },

                title = { Text("Nuevo deseo") },

                text = {

                    OutlinedTextField(
                        value = uiState.newWish,
                        onValueChange = {
                            viewModel.onNewWishChange(it)
                        },
                        label = { Text("Descripción del deseo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                },

                confirmButton = {

                    Button(
                        onClick = {
                            viewModel.addWish()
                            showDialog = false
                        }
                    ) {
                        Text("Agregar")
                    }
                },

                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}