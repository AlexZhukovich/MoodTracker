package com.alexzh.auth

import javax.crypto.spec.SecretKeySpec

interface Encryptor {
    val keySpec: SecretKeySpec

    fun encrypt(value: String): String
}