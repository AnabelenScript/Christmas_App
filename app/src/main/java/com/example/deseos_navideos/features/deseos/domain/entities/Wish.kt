package com.example.deseos_navideos.features.deseos.domain.entities

data class Wish(
    val id: Int,
    val wish: String,
    val idUser: Int,
    val username: String?,
    val state: String,
    val photoUrl: String?
)