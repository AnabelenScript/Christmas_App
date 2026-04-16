package com.example.deseos_navideos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.deseos_navideos.core.navigation.NavigationWrapper
import com.example.deseos_navideos.core.storage.UserSession
import com.example.deseos_navideos.core.sync.SyncWorker
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var usersRepository: UsersRepository

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("MainActivity", "Permiso de notificaciones concedido")
        } else {
            Log.d("MainActivity", "Permiso de notificaciones denegado")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        askNotificationPermission()
        syncTokenIfLogged()

        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavigationWrapper(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Ya concedido
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // Mostrar explicación si fuera necesario
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun syncTokenIfLogged() {
        lifecycleScope.launch {
            val userId = UserSession.getUserId()
            val role = UserSession.getRole()
            if (userId != null && role != null) {
                try {
                    val token = FirebaseMessaging.getInstance().token.await()
                    usersRepository.updateToken(token, userId, role)
                    
                    if (role == "padre") {
                        SyncWorker.startPeriodicSync(this@MainActivity)
                    }
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error sincronizando token: ${e.message}")
                }
            }
        }
    }
}
