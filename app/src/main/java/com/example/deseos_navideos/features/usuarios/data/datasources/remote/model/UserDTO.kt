package com.example.deseos_navideos.features.usuarios.data.datasources.remote.model

data class UserDTO(
    val id: Int,
    val username: String,
    val age: Int?,
    val country: String?,
    val good_kid: Int,
    val is_santa: Int,
    val password: String,
)
