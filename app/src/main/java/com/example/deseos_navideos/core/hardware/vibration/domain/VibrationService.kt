package com.example.deseos_navideos.core.hardware.vibration.domain

interface VibrationService {
    fun vibrate(duration: Long = 300)
}
