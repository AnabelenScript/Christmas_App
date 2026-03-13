package com.example.deseos_navideos.features.usuarios.data.datasources.remote.mapper

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidDashboardDTO
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidWishDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.WishDashboardDTO
import com.example.deseos_navideos.features.usuarios.domain.entities.Kid


fun KidDto.toDomain(): Kid {
    return Kid(
        id = id,
        username = username,
        audioUrl = audioUrl,
        wishes = wishes.map { it.toDomain(id, username) }
    )
}

fun KidWishDto.toDomain(idUser: Int, username: String): Wish {
    return Wish(
        id = id,
        wish = thing,
        idUser = idUser,
        username = username,
        state = state,
        photoUrl = photoUrl
    )
}

fun WishDashboardDTO.toDomain(idUser: Int, username: String): Wish {
    return Wish(
        id = id,
        wish = thing,
        idUser = idUser,
        username = username,
        state = state,
        photoUrl = photoUrl
    )
}

fun KidDashboardDTO.toDomain(): Kid {
    return Kid(
        id = id,
        username = username,
        audioUrl = audioUrl,
        wishes = wishes.map { it.toDomain(id, username) }
    )
}