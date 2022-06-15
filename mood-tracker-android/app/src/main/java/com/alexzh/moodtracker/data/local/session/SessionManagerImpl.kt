package com.alexzh.moodtracker.data.local.session

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alexzh.moodtracker.data.model.JwtToken

class SessionManagerImpl(
    private val sharedPreferences: SharedPreferences
): SessionManager {
    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_COOKIES = "auth_cookies"
    }

    override fun getCookies(): String? = sharedPreferences.getString(KEY_COOKIES, null)

    override fun getToken(): JwtToken = JwtToken(sharedPreferences.getString(KEY_TOKEN, null))

    override fun saveCookies(cookie: String?) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_COOKIES, cookie)
        }
    }

    override fun saveToken(token: JwtToken) {
        sharedPreferences.edit(commit = true) {
            putString(KEY_TOKEN, token.value)
        }
    }
}