package com.example.deseos_navideos.features.usuarios.data.datasources.remote.api

import com.example.deseos_navideos.features.login.data.datasources.model.LoginResponseDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.FamilyDashboardDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.MessageResponseDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UpdateUserDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UploadResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UsersApi {

    @GET("users/family/{code}/dashboard")
    suspend fun getFamilyDashboard(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("code") familyCode: String
    ): FamilyDashboardDto


    @GET("users/{id}")
    suspend fun getUser(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int
    ): LoginResponseDto


    @PUT("users/{id}")
    suspend fun updateUser(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int,
        @Body request: UpdateUserDto
    ): MessageResponseDto


    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int
    ): MessageResponseDto


    @Multipart
    @POST("users/{id}/audio")
    suspend fun uploadAudio(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int,
        @Part audio: MultipartBody.Part
    ): UploadResponseDto
}