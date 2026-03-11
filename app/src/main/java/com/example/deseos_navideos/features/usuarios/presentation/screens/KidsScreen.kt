package com.example.deseos_navideos.features.usuarios.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModel
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

@Composable
fun KidsScreen(
    viewModel: KidsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Niños de la familia",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {

            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {

                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = MaterialTheme.colorScheme.error
                )
            }

            uiState.kids.isEmpty() -> {

                Text("No hay niños registrados.")
            }

            else -> {

                LazyColumn {

                    items(uiState.kids) { kid ->

                        KidsCard(
                            kid = kid,
                            wishes = uiState.wishesByKid[kid.id] ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}