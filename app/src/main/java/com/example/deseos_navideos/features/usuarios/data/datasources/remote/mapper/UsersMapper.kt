package com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper

import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidWishDto
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid
import com.example.deseos_navideos.features.usuarios.domain.entities.Wish

fun KidDto.toDomain(): Kid {
    return Kid(
        id = id,
        username = username,
        audioUrl = audioUrl,
        wishes = wishes.map { it.toDomain() }
    )
}

fun KidWishDto.toDomain(): Wish {
    return Wish(
        id = id,
        wish = thing,
        state = state,
        photoUrl = photoUrl
    )
}