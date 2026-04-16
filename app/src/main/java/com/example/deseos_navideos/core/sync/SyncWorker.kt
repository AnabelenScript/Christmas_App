package com.example.deseos_navideos.core.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.deseos_navideos.core.database.daos.WishDao
import com.example.deseos_navideos.features.deseos.data.datasources.remote.api.WishesApi
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.CreateWishDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.api.UsersApi
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository
import com.example.deseos_navideos.core.storage.DataStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.io.File
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val wishDao: WishDao,
    private val wishesApi: WishesApi,
    private val usersApi: UsersApi,
    private val usersRepository: UsersRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "Iniciando sincronización...")
        
        pullSync()

        val pendingTasks = wishDao.getPendingTasks()
        if (pendingTasks.isEmpty()) return Result.success()

        var hasError = false

        pendingTasks.forEach { task ->
            try {
                val role = task.role ?: "child"
                when (task.taskType) {
                    "WISH" -> {
                        val response = wishesApi.addWish(
                            task.idUser,
                            role,
                            CreateWishDto(task.wish)
                        )
                        wishDao.markAsSynced(task.localId, response.id)
                    }
                    "PHOTO" -> {
                        val file = File(task.localFilePath ?: "")
                        if (file.exists()) {
                            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                            val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                            wishesApi.uploadPhoto(task.idUser, role, task.id, body)
                            wishDao.deleteByLocalId(task.localId)
                        } else {
                            wishDao.deleteByLocalId(task.localId)
                        }
                    }
                    "AUDIO" -> {
                        val file = File(task.localFilePath ?: "")
                        if (file.exists()) {
                            val requestFile = file.asRequestBody("audio/mpeg".toMediaTypeOrNull())
                            val body = MultipartBody.Part.createFormData("audio", file.name, requestFile)
                            usersApi.uploadAudio(task.idUser, role, task.id, body)
                            wishDao.deleteByLocalId(task.localId)
                        } else {
                            wishDao.deleteByLocalId(task.localId)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("SyncWorker", "Error sincronizando tarea ${task.localId}: ${e.message}")
                hasError = true
            }
        }

        return if (hasError) Result.retry() else Result.success()
    }

    private suspend fun pullSync() {
        Log.d("SyncWorker", "Iniciando pull sync...")
        val dataStorage = DataStorage(applicationContext)
        val loginRes = dataStorage.getLoginResponse()

        if (loginRes != null) {
            val user = loginRes.user
            if (user.role == "parent") {
                try {
                    Log.d("SyncWorker", "Descargando actualizaciones de los hijos para el padre...")
                    usersRepository.getKids(
                        familyCode = user.familyCode ?: "",
                        userId = user.id,
                        role = user.role
                    )
                    Log.d("SyncWorker", "Actualizaciones de hijos descargadas exitosamente.")
                } catch (e: Exception) {
                    Log.e("SyncWorker", "Error descargando actualizaciones: ${e.message}")
                }
            } else if (user.role == "child") {
                // Future expansion if needed
            }
        } else {
             Log.d("SyncWorker", "No hay usuario activo (DataStorage), se salta pullSync() ")
        }
    }

    companion object {
        fun startPeriodicSync(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
                
            val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .build()
                
            WorkManager.getInstance(context)
                .enqueueUniqueWork("parent_periodic_sync", ExistingWorkPolicy.KEEP, syncRequest)
        }
    }
}
