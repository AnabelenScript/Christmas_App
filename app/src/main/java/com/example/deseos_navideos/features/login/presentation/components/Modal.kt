package com.example.deseos_navideos.features.login.presentation.components


import androidx.compose.material3.*
import androidx.compose.runtime.Composable


@Composable
fun NavideñoModal(
    title: String,
    message: String,
    confirmText: String = "Aceptar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(confirmText, color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}
