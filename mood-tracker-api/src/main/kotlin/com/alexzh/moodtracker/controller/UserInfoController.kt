package com.alexzh.moodtracker.controller

import com.alexzh.moodtracker.data.UserRepository
import com.alexzh.moodtracker.data.model.UserInfo
import com.alexzh.moodtracker.model.UserSession
import com.alexzh.moodtracker.model.response.UserResponse
import io.ktor.server.sessions.*

class UserInfoController(
    private val userRepository: UserRepository
) {

    suspend fun getUserInfo(
        userId: Long,
        sessions: CurrentSession,
    ): UserResponse {
        val user = sessions.get<UserSession>()?.let {
            userRepository.getUserById(it.id)
        }
        return when {
            user == null -> UserResponse.Unauthorized("Not Authorized")
            user.id == userId -> UserResponse.Success(
                UserInfo(
                    id = user.id,
                    name = user.name,
                    email = user.email
                )
            )
            else -> UserResponse.Error("Problem retrieving active user information")
        }
    }
}