package com.alexzh.moodtracker.presentation.feature.profile

sealed class ProfileEvent {
    object CreateAccount: ProfileEvent()
    object LogIn: ProfileEvent()
    object GetUserInfo: ProfileEvent()
    object LogOut: ProfileEvent()
}
