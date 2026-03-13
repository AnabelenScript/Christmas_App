package com.example.deseos_navideos.features.login.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily

@Composable
fun HeaderCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF8F9FB),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mi Carta",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            )
            Text(
                text = "a Santa",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = bodyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
            )
            Divider(
                modifier = Modifier
                    .width(40.dp)
                    .padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color(0xFFE0E0E0)
            )
            Text(
                text = "¡Escribe tus deseos navideños\n y enviaselos a Santa!",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = bodyFontFamily
                ),
                color = Color.Gray,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}
