package com.example.deseos_navideos.features.detalles.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.deseos_navideos.core.theme.ui.bodyFontFamily
import com.example.deseos_navideos.features.detalles.presentation.viewmodel.KidDetailsViewModel
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KidDetailsScreen(
    navController: NavController,
    viewModel: KidDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isPlaying by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.loadKidDetails()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalles de niño", fontFamily = bodyFontFamily) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            when {
                uiState.isLoading && uiState.kid == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFFD32F2F))
                    }
                }
                uiState.errorMessage != null && uiState.kid == null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = uiState.errorMessage!!, color = Color.Red, fontFamily = bodyFontFamily)
                    }
                }
                uiState.kid != null -> {
                    val kid = uiState.kid!!
                    
                    Text(
                        text = kid.username,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = bodyFontFamily,
                            fontSize = 32.sp
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (kid.audioUrl != null) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFF1F3F4),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isPlaying) {
                                        viewModel.stopAudio()
                                        isPlaying = false
                                    } else {
                                        viewModel.playAudio(kid.audioUrl)
                                        isPlaying = true
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isPlaying) Color.Black else Color(0xFFD32F2F),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                                        contentDescription = if (isPlaying) "Detener audio" else "Reproducir audio",
                                        tint = Color.White,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = if (isPlaying) "Reproduciendo mensaje..." else "Mensaje de voz para Santa",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = bodyFontFamily
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    
                    Text(
                        text = "Sus deseos",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = bodyFontFamily
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(
                            items = kid.wishes,
                            key = { it.id }
                        ) { wish ->
                            WishDetailCard(
                                wish = wish,
                                onToggleState = { newState ->
                                    viewModel.updateWishState(wish.id, newState)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WishDetailCard(
    wish: Wish,
    onToggleState: (String) -> Unit
) {
    val isCompleted = wish.state.lowercase() in listOf("completed", "cumplido", "comprado")

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF1F3F4),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = wish.wish,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = bodyFontFamily,
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = {
                                if (!isCompleted) {
                                    onToggleState("Comprado")
                                }
                            },
                            enabled = !isCompleted,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                                contentDescription = if (isCompleted) "Cumplido" else "Marcar como comprado",
                                tint = if (isCompleted) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCompleted) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        ) {
                            Text(
                                text = (if (isCompleted) "CUMPLIDO" else "PENDIENTE"),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isCompleted) Color(0xFF2E7D32) else Color(0xFFD32F2F),
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = bodyFontFamily
                                )
                            )
                        }
                    }
                }
                
                if (wish.photoUrl != null) {
                    AsyncImage(
                        model = wish.photoUrl,
                        contentDescription = "Foto del deseo",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
