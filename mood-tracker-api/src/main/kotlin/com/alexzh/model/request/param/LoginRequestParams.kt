package com.alexzh.model.request.param

import com.alexzh.common.Email
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestParams(
    val email: Email,
    val password: String
)
