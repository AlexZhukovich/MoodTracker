package com.alexzh.moodtracker.data.local.session

import com.alexzh.moodtracker.data.model.JwtToken

interface SessionManager {

    fun getCookies(): String?

    fun getToken(): JwtToken

    fun saveCookies(cookie: String?)

    fun saveToken(token: JwtToken)
}