package com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper

import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UserDTO
import com.example.deseos_navideos.features.usuarios.domain.entities.Users

fun UserDTO.toDomain(): Users {
    return Users (
        id = this.id,
        name = this.username,
        age = this.age?: 1000,
        country = this.country?: "De otro mundo",
        good_kid = this.good_kid,
        is_santa = this.is_santa,
    )
}