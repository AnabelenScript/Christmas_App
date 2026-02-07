package com.example.deseos_navideos.features.deseos.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import com.example.deseos_navideos.features.deseos.presentation.viewmodel.WishesViewModel
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

@Composable
fun WishesScreen(viewModel: WishesViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier.weight(1f).height(120.dp), // más grande
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error) // rojo
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("${uiState.wishes.size}", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onError)
                    Text("Deseos", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onError)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                modifier = Modifier.weight(1f).height(120.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("25 Dic", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onError)
                    Text("Navidad", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onError)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        AddCard(viewModel)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Mis Deseos (${uiState.wishes.size})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            uiState.isLoading -> CircularProgressIndicator()

            uiState.errorMessage != null && uiState.wishes.isEmpty() -> Text(
                text = "Error: ${uiState.errorMessage}",
                color = MaterialTheme.colorScheme.error
            )

            else -> {
                if (uiState.wishes.isEmpty()) {
                    Text("Aún no has agregado ningún deseo. ¡Santa está esperando tu carta!")
                } else {
                    LazyColumn {
                        items(uiState.wishes) { wish ->
                            WishCard(wish, onDelete = { viewModel.removeWish(wish.id) })
                        }
                    }
                }
            }
        }
    }
}
