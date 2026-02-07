package com.example.deseos_navideos.features.usuarios.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deseos_navideos.features.usuarios.domain.entities.Users
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

@Composable
fun KidsCard(
    kid: Users,
    wishes: List<Wish>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(kid.name, style = MaterialTheme.typography.titleMedium)
                Text("${kid.age} años", style = MaterialTheme.typography.bodyMedium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("${wishes.size}", style = MaterialTheme.typography.titleMedium)
                Text("deseos", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
