package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.model.JwtToken
import com.alexzh.moodtracker.data.util.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun createAccount(
        name: String,
        email: String,
        password: String
    ): Flow<Result<JwtToken>>

    suspend fun logIn(
        email: String,
        password: String
    ): Flow<Result<JwtToken>>

    suspend fun logOut(): Flow<Result<Unit>>
}
