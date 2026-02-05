package com.example.deseos_navideos.features.login.data.datasources.model

data class UserDto(
    val id: Int,
    val username: String,
    val age: Int,
    val country: String?,
    val good_kid: Int,
    val is_santa: Int,
    val password: String
)