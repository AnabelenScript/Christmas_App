package com.example.deseos_navideos.features.login.data.datasources.api

import com.example.deseos_navideos.features.login.data.datasources.model.LoginRequestDto
import com.example.deseos_navideos.features.login.data.datasources.model.LoginResponseDto
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterResponseDto
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterUserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): LoginResponseDto

    @POST("users/")
    suspend fun register(
        @Body request: RegisterUserDto
    ): RegisterResponseDto
}