package com.alexzh.moodtracker.plugins

import com.alexzh.moodtracker.controller.AuthController
import com.alexzh.moodtracker.controller.UserInfoController
import com.alexzh.moodtracker.routes.authRoutes
import com.alexzh.moodtracker.routes.userInfoRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Application.configureRouting() {
    val authController: AuthController by inject(AuthController::class.java)
    val userInfoController: UserInfoController by inject(UserInfoController::class.java)

    routing {
        authRoutes(authController)
        userInfoRoutes(userInfoController)
    }
}
