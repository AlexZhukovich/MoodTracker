package com.alexzh.model.response

import io.ktor.http.*

sealed class AuthResponse(val status: HttpStatusCode) {

    class Success(
        val token: String?
    ): AuthResponse(HttpStatusCode.OK)

    class Error(
        val message: String
    ): AuthResponse(HttpStatusCode.BadRequest)
}
