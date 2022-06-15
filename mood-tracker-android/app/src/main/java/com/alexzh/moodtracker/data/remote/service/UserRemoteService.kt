package com.alexzh.moodtracker.data.remote.service

import com.alexzh.moodtracker.data.remote.model.CreateUserModel
import com.alexzh.moodtracker.data.remote.model.LoginUserModel
import com.alexzh.moodtracker.data.remote.model.UserInfoModel
import retrofit2.Response
import retrofit2.http.*

interface UserRemoteService {

    @Headers("Content-Type: application/json")
    @POST("users/create")
    suspend fun createUser(
        @Body user: CreateUserModel
    ): Response<String>

    @Headers("Content-Type: application/json")
    @POST("users/login")
    suspend fun login(
        @Body user: LoginUserModel
    ): Response<String>

    @Headers("Content-Type: application/json")
    @POST("users/logout")
    suspend fun logout(): Response<Unit>

    @GET("users/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Long,
    ): Response<UserInfoModel>
}