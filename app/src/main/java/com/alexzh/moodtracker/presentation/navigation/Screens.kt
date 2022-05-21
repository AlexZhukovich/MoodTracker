package com.alexzh.moodtracker.presentation.navigation

sealed class Screens(val route: String) {
    object TodayScreen : Screens("todayScreen")
    object AddMoodScreen : Screens("addMoodScreen")
}
