package com.example.deseos_navideos.core.hardware.audio.domain

import java.io.File

interface AudioService {
    fun startRecording(file: File)
    fun stopRecording()
    fun isRecording(): Boolean
}
