package com.alexzh.moodtracker.routes

import com.alexzh.moodtracker.auth.principal.UserPrincipal
import com.alexzh.moodtracker.controller.UserInfoController
import com.alexzh.moodtracker.model.response.UserResponse
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userInfoRoutes(
    userInfoController: UserInfoController
) {
    route("/users") {
        authenticate {
            get("/{id}") {
                val userId: Long = call.parameters["id"]?.toLong() ?: -1L
                val user: UserPrincipal? = call.principal()

                when (val response = userInfoController.getUserInfo(userId, user)) {
                    is UserResponse.Success -> call.respond(response.status, response.userInfo)
                    is UserResponse.Error -> call.respond(response.status, response.message)
                }
            }
        }
    }
}