package com.example.deseos_navideos.core.storage

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserSession {

    private val _userId = MutableStateFlow<Int?>(null)
    val userId = _userId.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _role = MutableStateFlow<String?>(null)
    val role = _role.asStateFlow()

    private val _familyCode = MutableStateFlow<String?>(null)
    val familyCode = _familyCode.asStateFlow()

    fun login(
        id: Int,
        username: String,
        role: String,
        familyCode: String?
    ) {
        _userId.value = id
        _username.value = username
        _role.value = role
        _familyCode.value = familyCode
    }

    fun logout() {
        _userId.value = null
        _username.value = null
        _role.value = null
        _familyCode.value = null
    }

    fun isLoggedIn(): Boolean {
        return _userId.value != null
    }

    fun getUserId(): Int? {
        return _userId.value
    }

    fun getRole(): String? {
        return _role.value
    }

    fun getFamilyCode(): String? {
        return _familyCode.value
    }
}