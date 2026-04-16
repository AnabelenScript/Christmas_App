package com.example.deseos_navideos.features.notificaciones.domain.entities

data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val idUser: Int
)
