package com.example.deseos_navideos.features.usuarios.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid

@Composable
fun KidsCard(
    kid: Kid,
    onDetailsClick: () -> Unit
) {
    val totalWishes = kid.wishes.size
    // Normalizamos a minúsculas para comparar todos los posibles estados de éxito
    val completedWishes = kid.wishes.count { 
        it.state.lowercase() in listOf("completed", "cumplido", "comprado") 
    }
    val progress = if (totalWishes > 0) completedWishes.toFloat() / totalWishes else 0f
    val percentage = (progress * 100).toInt()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = kid.username,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = bodyFontFamily,
                            fontSize = 24.sp
                        )
                    )
                    Text(
                        text = "$completedWishes de $totalWishes deseos cumplidos",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontFamily = bodyFontFamily
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.size(width = 60.dp, height = 40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold,
                                fontFamily = bodyFontFamily
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF2E7D32),
                trackColor = Color(0xFFF1F3F4)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onDetailsClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF1F3F4),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ver sus deseos",
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
