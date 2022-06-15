package com.alexzh.moodtracker.presentation.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.data.AuthRepository
import com.alexzh.moodtracker.data.UserRepository
import com.alexzh.moodtracker.data.util.Result
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
): ViewModel() {
    companion object {
        const val NAME = "Alex.Z_42"
        const val EMAIL = "test-account@alexzh.com"
        const val PASSWORD = "12345"
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.CreateAccount -> createAccount()
            is ProfileEvent.LogIn -> login()
            is ProfileEvent.GetUserInfo -> getUserInfo()
            is ProfileEvent.LogOut -> logout()
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            when (val result = authRepository.createAccount(NAME, EMAIL, PASSWORD)) {
                is Result.Loading -> {
                    println("ProfileViewModel => CREATE => LOADING...")
                }
                is Result.Success -> {
                    println("ProfileViewModel => CREATE => SUCCESS => ${result.data}")
                }
                is Result.Error -> {
                    println("ProfileViewModel => CREATE => ERROR = >${result.cause}")
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            when (val result = authRepository.logIn(EMAIL, PASSWORD)) {
                is Result.Loading -> {
                    println("ProfileViewModel => LOGIN => LOADING...")
                }
                is Result.Success -> {
                    println("ProfileViewModel => LOGIN => SUCCESS => ${result.data}")
                }
                is Result.Error -> {
                    println("ProfileViewModel => LOGIN => ERROR => ${result.cause}")
                }
            }
        }
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            when(val result = userRepository.getUserInfo(userId = 1)) {
                is Result.Loading -> {
                    println("ProfileViewModel => GET_USER_INFO => LOADING...")
                }
                is Result.Success -> {
                    println("ProfileViewModel => GET_USER_INFO => SUCCESS => ${result.data}")
                }
                is Result.Error -> {
                    println("ProfileViewModel => GET_USER_INFO => ERROR => ${result.cause}")
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            when (val result = authRepository.logOut()) {
                is Result.Loading -> {
                    println("ProfileViewModel => LOGOUT => LOADING...")
                }
                is Result.Success -> {
                    println("ProfileViewModel => LOGOUT => SUCCESS")
                }
                is Result.Error -> {
                    println("ProfileViewModel => LOGOUT => ERROR => ${result.cause}")
                }
            }
        }
    }
}