package com.example.deseos_navideos.features.usuarios.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deseos_navideos.features.usuarios.presentation.viewmodel.KidsViewModel
import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

@Composable
fun KidsScreen(
    viewModel: KidsViewModel
) {
    val kids: List<Users> by viewModel.kids.collectAsState(initial = emptyList())
    val wishesByKid: Map<Int, List<Wish>> by viewModel.wishesByKid.collectAsState(initial = emptyMap())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ho Ho Ho, Santa! Panel de control del Polo Norte",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("${kids.size}", style = MaterialTheme.typography.titleLarge)
                    Text("Niños", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    val totalWishes = wishesByKid.values.sumOf { it.size }
                    Text("$totalWishes", style = MaterialTheme.typography.titleLarge)
                    Text("Deseos", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Lista de Niños (${kids.size})", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))
        kids.forEach { kid ->
            KidsCard(
                kid = kid,
                wishes = wishesByKid[kid.id] ?: emptyList()
            )
        }
    }
}

