package com.example.deseos_navideos.features.detalles.domain.entities

import com.example.deseos_navideos.features.deseos.domain.entities.Wish

data class KidDetails(
    val id: Int,
    val username: String,
    val audioUrl: String?,
    val wishes: List<Wish>
)
