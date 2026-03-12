package com.example.deseos_navideos.features.usuarios.domain.entities

import com.example.deseos_navideos.features.deseos.domain.entities.Wish

data class Kid(
    val id: Int,
    val username: String,
    val audioUrl: String?,
    val wishes: List<Wish>
)

/*
data class Wish(
    val id: Int,
    val wish: String,
    val state: String,
    val photoUrl: String?
)
 */