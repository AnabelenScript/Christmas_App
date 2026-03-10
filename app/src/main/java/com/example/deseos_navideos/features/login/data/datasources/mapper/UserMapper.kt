package com.example.deseos_navideos.features.login.data.datasources.mapper
import com.example.deseos_navideos.features.login.data.datasources.model.UserDto
import com.example.deseos_navideos.features.login.domain.entities.User

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        username = this.username,
        age = this.age,
        country = this.country,
        password = this.password,
        familyCode = this.familyCode,
        role = this.role
    )
}
