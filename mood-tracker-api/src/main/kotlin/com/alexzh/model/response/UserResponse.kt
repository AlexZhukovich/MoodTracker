package com.alexzh.model.response

import com.alexzh.data.model.UserInfo
import io.ktor.http.*

sealed class UserResponse(val status: HttpStatusCode) {

    class Success(
        val userInfo: UserInfo
    ): UserResponse(HttpStatusCode.OK)

    class Error(
        val message: String
    ): UserResponse(HttpStatusCode.BadRequest)
}