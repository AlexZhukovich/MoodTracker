package com.alexzh.auth.principal

import com.alexzh.data.model.User
import io.ktor.server.auth.*

class UserPrincipal(
    val user: User
): Principal
