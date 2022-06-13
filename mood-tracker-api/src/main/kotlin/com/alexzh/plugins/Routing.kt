package com.alexzh.plugins

import com.alexzh.controller.AuthController
import com.alexzh.controller.UserInfoController
import com.alexzh.routes.authRoutes
import com.alexzh.routes.userInfoRoutes
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
