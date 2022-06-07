package com.alexzh.moodtracker.presentation.navigation

private const val FULL_ADD_MOOD_SCREEN_ROUTE = "${Screens.AddMoodScreen.prefix}/{${Screens.AddMoodScreen.paramName}}";

sealed class Screens(val route: String) {
    object TodayScreen : Screens("todayScreen")
    object AddMoodScreen : Screens(FULL_ADD_MOOD_SCREEN_ROUTE) {
        const val prefix = "addMoodScreen"
        const val paramName = "emotionHistoryId"

        fun createRoute(emotionHistoryId: Long) = "$prefix/${emotionHistoryId}"
    }
}
