package com.example.deseos_navideos.core.hardware.camara.domain

import android.net.Uri
import java.io.File

interface CamaraService {
    fun createImageUri(): Uri
    fun getFileFromUri(uri: Uri): File?
}
