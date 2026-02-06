package com.example.deseos_navideos.features.deseos.domain.entities

data class Wish(
    val id: Int,
    val wish: String,
    val id_user: Int,
    val is_editing: Boolean
)
