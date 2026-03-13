package com.example.deseos_navideos.features.detalles.data.datasources.remote.mapper

import com.example.deseos_navideos.features.deseos.domain.entities.Wish
import com.example.deseos_navideos.features.detalles.domain.entities.KidDetails
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.KidWishDto

fun KidDto.toDetailsDomain(): KidDetails {
    return KidDetails(
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
