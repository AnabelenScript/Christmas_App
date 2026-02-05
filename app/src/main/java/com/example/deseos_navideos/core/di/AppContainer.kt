package com.example.deseos_navideos.core.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository
import com.example.deseos_navideos.features.login.data.repositories.AuthRepositoryImpl
import android.content.Context

class AppContainer(context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://100.50.208.4")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: DeseosApi by lazy {
        retrofit.create(DeseosApi::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(apiService)
    }
}
