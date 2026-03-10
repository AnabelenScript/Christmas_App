package com.example.deseos_navideos.core.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.deseos_navideos.features.login.data.datasources.model.LoginRes
import com.google.gson.Gson

class DataStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("deseos_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveLoginResponse(loginRes: LoginRes) {
        val json = gson.toJson(loginRes)
        prefs.edit().putString("login_response", json).apply()
    }

    fun getLoginResponse(): LoginRes? {
        val json = prefs.getString("login_response", null)
        return json?.let { gson.fromJson(it, LoginRes::class.java) }
    }

    fun clearLoginResponse() {
        prefs.edit().remove("login_response").apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}
