package com.example.deseos_navideos.features.usuarios.domain.entities

data class Users(
    val id: Int,
    val name: String,
    val age: Int,
    val country: String,
    val good_kid: Boolean,
    val is_santa: Boolean
)
