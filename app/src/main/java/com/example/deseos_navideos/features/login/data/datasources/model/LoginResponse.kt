package com.example.deseos_navideos.features.login.data.datasources.model
import com.example.deseos_navideos.features.login.data.datasources.model.UserDto

data class LoginRes(
    val user: UserDto,
    val role: String
)
