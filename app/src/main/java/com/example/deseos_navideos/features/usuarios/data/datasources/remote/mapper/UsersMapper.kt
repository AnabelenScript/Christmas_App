package com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper

import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UserDTO
import com.example.deseos_navideos.features.usuarios.domain.entities.Users

fun UserDTO.toDomain(): Users {
    return Users (
        id = this.id,
        username = this.username,
        age = this.age ?: 0,
        country = this.country ?: "",
        password = this.password
    )
}
