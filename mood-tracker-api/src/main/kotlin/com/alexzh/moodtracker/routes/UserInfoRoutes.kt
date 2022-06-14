package com.alexzh.moodtracker.routes

import com.alexzh.moodtracker.controller.UserInfoController
import com.alexzh.moodtracker.model.response.UserResponse
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.userInfoRoutes(
    userInfoController: UserInfoController,
) {
    route("/api/v1/users") {
        authenticate("jwt") {
            get("/{id}") {
                val userId: Long = call.parameters["id"]?.toLong() ?: -1L
                when (val response = userInfoController.getUserInfo(userId, call.sessions)) {
                    is UserResponse.Success -> call.respond(response.status, response.userInfo)
                    is UserResponse.Error -> call.respond(response.status, response.message)
                    is UserResponse.Unauthorized -> call.respond(response.status, response.message)
                }
            }
        }
    }
}