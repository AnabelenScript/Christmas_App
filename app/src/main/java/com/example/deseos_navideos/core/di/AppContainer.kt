package com.example.deseos_navideos.core.di

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.deseos_navideos.core.network.DeseosApi
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository
import com.example.deseos_navideos.features.login.data.repositories.AuthRepositoryImpl
import android.content.Context
import com.example.deseos_navideos.core.storage.DataStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.example.deseos_navideos.features.deseos.data.repositories.WishesRepositoryImpl
import com.example.deseos_navideos.features.deseos.domain.repositories.WishesRepository
import com.example.deseos_navideos.features.usuarios.data.repository.UsersRepositoryImpl
import com.example.deseos_navideos.features.usuarios.domain.repositories.UsersRepository

class AppContainer(context: Context) {

    val dataStorage: DataStorage by lazy {
        DataStorage(context)
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://100.50.208.4/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: DeseosApi by lazy {
        retrofit.create(DeseosApi::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(apiService, dataStorage)
    }

    val wishesRepository: WishesRepository by lazy {
        WishesRepositoryImpl(apiService)
    }

    val usersRepository: UsersRepository by lazy {
        UsersRepositoryImpl(apiService)
    }
}
