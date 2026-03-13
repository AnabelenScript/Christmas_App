package com.example.deseos_navideos.features.deseos.data.datasources.remote.mapper

import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishDto
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

fun WishDto.toDomain(): Wish {
    return Wish(
        id = id,
        wish = thing,
        idUser = idUser,
        username = username,
        state = state,
        photoUrl = photoUrl
    )
}