package com.example.deseos_navideos.features.login.domain.usecases
import com.example.deseos_navideos.features.login.domain.entities.User
import com.example.deseos_navideos.features.login.domain.repositories.AuthRepository


class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): User {
        return repository.login(username, password)
    }
}

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, age: Int, country: String, password: String): Int {
        return repository.register(username, age, country, password)
    }
}

