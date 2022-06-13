package com.alexzh.controller

import com.alexzh.auth.principal.UserPrincipal
import com.alexzh.data.model.UserInfo
import com.alexzh.model.response.UserResponse

class UserInfoController {

    fun getUserInfo(
        userId: Long,
        userPrincipal: UserPrincipal?
    ): UserResponse {
        return when {
            userPrincipal == null -> UserResponse.Error("User should be logged in")
            userPrincipal.user.id == userId -> UserResponse.Success(
                UserInfo(
                    id = userPrincipal.user.id,
                    name = userPrincipal.user.name,
                    email = userPrincipal.user.email
                )
            )
            else -> UserResponse.Error("Problem retrieving active user information")
        }
    }
}