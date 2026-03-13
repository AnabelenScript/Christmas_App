package com.example.deseos_navideos.features.deseos.data.datasources.local.mapper

import com.example.deseos_navideos.core.database.entities.WishEntity
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishDto
import com.example.deseos_navideos.features.deseos.domain.entities.Wish

fun WishDto.toEntity(): WishEntity {
    return WishEntity(
        id = id,
        wish = thing,
        idUser = idUser,
        username = username,
        state = state,
        photoUrl = photoUrl,
        syncState = "SYNCED"
    )
}

fun WishEntity.toDomain(): Wish {
    return Wish(
        id = id,
        wish = wish,
        idUser = idUser,
        username = username,
        state = state,
        photoUrl = photoUrl
    )
}