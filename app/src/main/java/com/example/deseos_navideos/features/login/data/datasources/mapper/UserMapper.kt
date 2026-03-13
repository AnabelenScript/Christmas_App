package com.example.deseos_navideos.features.login.data.datasources.mapper
import com.example.deseos_navideos.core.database.entities.UserEntity
import com.example.deseos_navideos.features.login.data.datasources.model.UserDto
import com.example.deseos_navideos.features.login.domain.entities.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        age = age,
        country = country,
        password = password,
        familyCode = familyCode,
        role = role
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        age = age,
        country = country,
        password = password,
        familyCode = familyCode,
        role = role
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        username = username,
        age = age,
        country = country,
        password = password,
        familyCode = familyCode,
        role = role
    )
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        age = age,
        country = country,
        password = password,
        familyCode = familyCode,
        role = role
    )
}