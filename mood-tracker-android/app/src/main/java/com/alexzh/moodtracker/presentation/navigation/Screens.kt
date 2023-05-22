package com.alexzh.moodtracker.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.design.R as desR

private const val FULL_TODAY_SCREEN_ROUTE = "todayScreen"
private const val FULL_ADD_MOOD_SCREEN_ROUTE = "${Screens.AddMoodScreen.prefix}/{${Screens.AddMoodScreen.paramName}}"
private const val FULL_STATISTICS_SCREEN_ROUTE = "statisticsScreen"
private const val FULL_SETTINGS_SCREEN_ROUTE = "settingsScreen"
private const val FULL_PROFILE_SCREEN_ROUTE = "profileScreen"
private const val FULL_CREATE_ACCOUNT_SCREEN_ROUTE = "createAccountScreen"
private const val FULL_LOGIN_SCREEN_ROUTE = "${Screens.LoginScreen.prefix}/{${Screens.LoginScreen.paramName}}"

sealed class Screens(val route: String) {
    object TodayScreen : Screens(FULL_TODAY_SCREEN_ROUTE) {
        fun toNavItem(): NavItem {
            return NavItem(
                title = R.string.navigation_today_label,
                icon = desR.drawable.ic_nav_today,
                route = FULL_TODAY_SCREEN_ROUTE
            )
        }
    }
    object AddMoodScreen : Screens(FULL_ADD_MOOD_SCREEN_ROUTE) {
        const val prefix = "addMoodScreen"
        const val paramName = "emotionHistoryId"

        fun createRoute(emotionHistoryId: Long) = "$prefix/${emotionHistoryId}"
    }
    object StatisticsScreen : Screens(FULL_STATISTICS_SCREEN_ROUTE) {
        fun toNavItem(): NavItem {
            return NavItem(
                title = R.string.navigation_statistics_label,
                icon = R.drawable.ic_nav_stats,
                route = FULL_STATISTICS_SCREEN_ROUTE
            )
        }
    }
    object SettingsScreen : Screens(FULL_SETTINGS_SCREEN_ROUTE) {
        fun toNavItem(): NavItem {
            return NavItem(
                title = R.string.navigation_settings_label,
                icon = R.drawable.ic_nav_settings,
                route = FULL_SETTINGS_SCREEN_ROUTE
            )
        }
    }
    object ProfileScreen : Screens(FULL_PROFILE_SCREEN_ROUTE)

    object CreateAccountScreen : Screens(FULL_CREATE_ACCOUNT_SCREEN_ROUTE)

    object LoginScreen : Screens(FULL_LOGIN_SCREEN_ROUTE) {
        const val prefix = "loginScreen"
        const val paramName = "destination"
        const val NO_REDIRECT = "-"

        fun createRoute(redirectToPath: String = NO_REDIRECT) = "$prefix/$redirectToPath"
    }
}

data class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
)