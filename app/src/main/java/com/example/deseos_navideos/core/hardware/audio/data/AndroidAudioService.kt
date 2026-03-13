package com.example.deseos_navideos.core.hardware.audio.data

import android.content.Context
import android.media.MediaPlayer
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
    private var mediaPlayer: MediaPlayer? = null
    private var _isRecording = false
    private var _isPlaying = false

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
        _isRecording = true
    }

    override fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            release()
        }
        mediaRecorder = null
        _isRecording = false
    }

    override fun isRecording(): Boolean = _isRecording

    override fun playAudio(url: String) {
        stopPlayback()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { 
                start()
                _isPlaying = true
            }
            setOnCompletionListener {
                _isPlaying = false
                stopPlayback()
            }
        }
    }

    override fun stopPlayback() {
        mediaPlayer?.apply {
            if (_isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
        _isPlaying = false
    }

    override fun isPlaying(): Boolean = _isPlaying
}
