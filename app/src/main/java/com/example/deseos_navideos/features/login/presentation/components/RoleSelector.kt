package com.example.deseos_navideos.features.login.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun RoleSelector(
    selectedRole: String,
    onRoleChange: (String) -> Unit
) {

    Row (
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            RadioButton(
                selected = selectedRole == "child",
                onClick = { onRoleChange("child") }
            )

            Text("Niño")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            RadioButton(
                selected = selectedRole == "parent",
                onClick = { onRoleChange("parent") }
            )

            Text("Padre")
        }
    }
}