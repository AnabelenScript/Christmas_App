package com.example.deseos_navideos.features.usuarios.domain.entities

data class Users(
    val id: Int,
    val username: String,
    val age: Int,
    val country: String,
    val password: String
)
