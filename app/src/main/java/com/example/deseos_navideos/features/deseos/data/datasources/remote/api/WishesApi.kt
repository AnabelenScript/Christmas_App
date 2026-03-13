package com.example.deseos_navideos.features.deseos.data.datasources.remote.api

import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.CreateWishDto
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishDto
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.UpdateWishStateDto
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishesResponseDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.MessageResponseDto
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UploadResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface WishesApi {

    @POST("wishes/")
    suspend fun addWish(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Body request: CreateWishDto
    ): MessageResponseDto


    @GET("wishes/{code}")
    suspend fun getWishes(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("code") familyCode: String
    ): WishesResponseDto


    @PUT("wishes/{id}")
    suspend fun updateWish(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int,
        @Body request: UpdateWishDto
    ): MessageResponseDto


    @PATCH("wishes/{id}")
    suspend fun updateWishState(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int,
        @Body request: UpdateWishStateDto
    ): MessageResponseDto


    @DELETE("wishes/{id}")
    suspend fun deleteWish(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int
    ): MessageResponseDto


    @Multipart
    @POST("wishes/{id}/photo")
    suspend fun uploadPhoto(
        @Header("x-user-id") userId: Int,
        @Header("x-role") role: String,
        @Path("id") id: Int,
        @Part photo: MultipartBody.Part
    ): UploadResponseDto
}
