package com.example.deseos_navideos.core.network

import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.GenericResponse
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishesReq
import com.example.deseos_navideos.features.deseos.data.datasources.remote.model.WishesResponse
import com.example.deseos_navideos.features.login.data.datasources.model.LoginRequest
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterReq
import com.example.deseos_navideos.features.login.data.datasources.model.LoginRes
import com.example.deseos_navideos.features.login.data.datasources.model.RegisterRes
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UserReq
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UserResponse
import com.example.deseos_navideos.features.usuarios.data.datasources.remote.model.UsersResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface DeseosApi {
    // Módulo Auth
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): LoginRes

    @POST("users/")
    suspend fun register(@Body request: RegisterReq): RegisterRes

    // Módulo de usuarios
    @GET("users/family/{code}/dashboard")
    suspend fun getKids(
        @Path("code") code: String,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): UsersResponse

    @GET("users/{id}")
    suspend fun getOneUser(
        @Path("id") id: Int,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): UserResponse

    @PUT("users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body req: UserReq,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @PATCH("users/{id}")
    suspend fun updateGoodness(
        @Path("id") id: Int,
        @Body req: Map<String, Int>,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @DELETE("users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @Multipart
    @POST("users/{id}/audio")
    suspend fun uploadAudio(
        @Path("id") id: Int,
        @Part audio: MultipartBody.Part,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    // Módulo de deseos
    @POST("wishes/")
    suspend fun createWish(
        @Body req: WishesReq,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @GET("wishes/{code}")
    suspend fun getWishes(
        @Path("code") code: String,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): WishesResponse

    @PUT("wishes/{id}")
    suspend fun updateWish(
        @Path("id") id: Int,
        @Body req: WishesReq,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @PATCH("wishes/{id}")
    suspend fun updateWishState(
        @Path("id") id: Int,
        @Body req: Map<String, String>, // {"state": "Comprado"}
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @DELETE("wishes/{id}")
    suspend fun deleteWish(
        @Path("id") id: Int,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse

    @Multipart
    @POST("wishes/{id}/photo")
    suspend fun uploadWishPhoto(
        @Path("id") id: Int,
        @Part photo: MultipartBody.Part,
        @Header("x-user-id") x_user_id: Int,
        @Header("x-role") x_role: String,
    ): GenericResponse
}