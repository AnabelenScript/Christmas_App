package com.example.deseos_navideos.core.hardware.camara.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.deseos_navideos.core.hardware.camara.domain.CamaraService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class AndroidCamaraService @Inject constructor(
    @ApplicationContext private val context: Context
) : CamaraService {
    private var lastCreatedFile: File? = null

    override fun createImageUri(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir("Pictures")
        val file = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        lastCreatedFile = file
        
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    override fun getFileFromUri(uri: Uri): File? {
        if (lastCreatedFile != null && lastCreatedFile!!.exists()) {
             return compressImage(lastCreatedFile!!)
        }

        val path = uri.path ?: return null
        val fileName = path.substringAfterLast("/")
        val storageDir = context.getExternalFilesDir("Pictures")
        val file = File(storageDir, fileName)
        
        if (file.exists()) {
            return compressImage(file)
        }
        
        val recentFile = storageDir?.listFiles()
            ?.filter { it.extension == "jpg" || it.extension == "jpeg" }
            ?.maxByOrNull { it.lastModified() }
            
        return recentFile?.let { compressImage(it) }
    }

    private fun compressImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.absolutePath) ?: return file
        
        val maxSize = 1200
        val width = bitmap.width
        val height = bitmap.height
        
        val resizedBitmap = if (width > maxSize || height > maxSize) {
            val aspectRatio = width.toFloat() / height.toFloat()
            val newWidth: Int
            val newHeight: Int
            if (width > height) {
                newWidth = maxSize
                newHeight = (maxSize / aspectRatio).toInt()
            } else {
                newHeight = maxSize
                newWidth = (maxSize * aspectRatio).toInt()
            }
            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        } else {
            bitmap
        }

        val compressedFile = File(context.cacheDir, "compressed_${file.name}")
        FileOutputStream(compressedFile).use { out ->
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
        }
        
        return compressedFile
    }
}
