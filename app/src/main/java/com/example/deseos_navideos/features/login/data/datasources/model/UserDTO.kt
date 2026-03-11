package com.example.deseos_navideos.features.login.data.datasources.model

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    val user: UserDto
)

data class UserDto(
    val id: Int,
    val username: String,
    val age: Int,
    val country: String?,
    val password: String,
    @SerializedName("family_code")
    val familyCode: String?,
    val role: String
)

data class RegisterUserDto(
    val username: String,
    val age: Int,
    val country: String,
    val password: String,
    val role: String,
    @SerializedName("family_code")
    val familyCode: String?
)

data class LoginRequestDto(
    val username: String,
    val password: String
)

data class RegisterResponseDto(
    val id: Int
)

data class LoginRes(
    val user: UserDto
)