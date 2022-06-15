package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.model.JwtToken
import com.alexzh.moodtracker.data.util.Result

interface AuthRepository {

    suspend fun createAccount(
        name: String,
        email: String,
        password: String
    ): Result<JwtToken>

    suspend fun logIn(
        email: String,
        password: String
    ) : Result<JwtToken>

    suspend fun logOut(): Result<Unit>
}
