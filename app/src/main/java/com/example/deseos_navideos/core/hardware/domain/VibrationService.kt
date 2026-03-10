package com.example.deseos_navideos.core.hardware.domain

interface VibrationService {
    fun vibrate(duration: Long = 200)
}
