package com.alexzh.moodtracker.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.alexzh.moodtracker.R

private const val FULL_TODAY_SCREEN_ROUTE = "todayScreen"
private const val FULL_ADD_MOOD_SCREEN_ROUTE = "${Screens.AddMoodScreen.prefix}/{${Screens.AddMoodScreen.paramName}}";
private const val FULL_STATISTICS_SCREEN_ROUTE = "statisticsScreen"


sealed class Screens(val route: String) {
    object TodayScreen : Screens(FULL_TODAY_SCREEN_ROUTE) {
        fun toNavItem(): NavItem {
            return NavItem(
                title = R.string.navigation_statistics_label,
                icon = R.drawable.ic_nav_today,
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
}

// TODO: NAV ICON
data class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
)