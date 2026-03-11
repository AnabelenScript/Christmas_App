package com.example.deseos_navideos.core.hardware.audio.data

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import com.example.deseos_navideos.core.hardware.audio.domain.AudioService
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AndroidAudioService @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioService {

    private var mediaRecorder: MediaRecorder? = null
    private var isRecording = false

    override fun startRecording(file: File) {
        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            @Suppress("DEPRECATION")
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file.absolutePath)
            prepare()
            start()
        }
        isRecording = true
    }

    override fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        isRecording = false
    }

    override fun isRecording(): Boolean = isRecording
}
