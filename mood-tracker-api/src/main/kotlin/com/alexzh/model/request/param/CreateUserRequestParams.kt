package com.alexzh.model.request.param

import com.alexzh.common.Email
import com.alexzh.common.Name
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestParams(
    val username: Name,
    val email: Email,
    val password: String
)
