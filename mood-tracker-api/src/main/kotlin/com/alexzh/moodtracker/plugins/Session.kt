package com.alexzh.moodtracker.plugins

import com.alexzh.moodtracker.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSession(
    sessionEncryptKey: String,
    sessionAuthKey: String
) {
    install(Sessions) {
        val secretEncryptKey = hex(sessionEncryptKey)
        val secretAuthKey = hex(sessionAuthKey)

        cookie<UserSession>(
            name = "USER_SESSION",
            storage = SessionStorageMemory()
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }
}