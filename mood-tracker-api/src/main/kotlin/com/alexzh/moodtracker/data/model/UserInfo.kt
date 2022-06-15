package com.alexzh.moodtracker.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val id: Long,
    val username: String,
    val email: String
)
