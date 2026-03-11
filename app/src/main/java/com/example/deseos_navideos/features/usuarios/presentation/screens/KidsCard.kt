package com.example.deseos_navideos.features.usuarios.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid

@Composable
fun KidsCard(
    kid: Kid,
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

                Text(
                    text = kid.username,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "${9} años",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = "${wishes.size}",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "deseos",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}