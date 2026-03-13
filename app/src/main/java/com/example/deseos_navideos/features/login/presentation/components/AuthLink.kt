package com.example.deseos_navideos.features.login.presentation.components

import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController

@Composable
fun AuthLink(
    normalText: String,
    linkText: String,
    navigateTo: String,
    navController: NavController
) {
    val annotatedText = buildAnnotatedString {
        append("$normalText ")

        pushStringAnnotation(tag = "authLink", annotation = navigateTo)
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        ) {
            append(linkText)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "authLink", start = offset, end = offset)
                .firstOrNull()?.let {
                    navController.navigate(navigateTo)
                }
        }
    )
}
