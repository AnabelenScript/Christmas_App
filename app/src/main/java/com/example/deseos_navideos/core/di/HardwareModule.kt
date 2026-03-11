package com.example.deseos_navideos.core.di

import android.content.Context
import com.example.deseos_navideos.core.hardware.audio.data.AndroidAudioService
import com.example.deseos_navideos.core.hardware.audio.domain.AudioService
import com.example.deseos_navideos.core.hardware.camara.data.AndroidCamaraService
import com.example.deseos_navideos.core.hardware.camara.domain.CamaraService
import com.example.deseos_navideos.core.hardware.vibration.data.AndroidVibrationService
import com.example.deseos_navideos.core.hardware.vibration.domain.VibrationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HardwareModule {

    @Provides
    @Singleton
    fun provideVibrationService(@ApplicationContext context: Context): VibrationService {
        return AndroidVibrationService(context)
    }

    @Provides
    @Singleton
    fun provideCamaraService(@ApplicationContext context: Context): CamaraService {
        return AndroidCamaraService(context)
    }

    @Provides
    @Singleton
    fun provideAudioService(@ApplicationContext context: Context): AudioService {
        return AndroidAudioService(context)
    }
}
