package com.alexzh.moodtracker.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.alexzh.moodtracker.presentation.feature.addmood.AddMoodScreen
import com.alexzh.moodtracker.presentation.feature.stats.StatisticsScreen
import com.alexzh.moodtracker.presentation.feature.today.TodayScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.get

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun AppNavigation(
    navController: NavHostController,
    isBottomBarDisplayed: MutableState<Boolean>
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.TodayScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(400)) },
        exitTransition = { fadeOut(animationSpec = tween(400)) }
    ) {
        composable(
            route = Screens.TodayScreen.route,
        ) {
            LaunchedEffect(Unit) {
                isBottomBarDisplayed.value = true
            }
            TodayScreen(
                viewModel = get(),
                onAdd = { navController.navigate(Screens.AddMoodScreen.createRoute(-1L)) },
                onEdit = { navController.navigate(Screens.AddMoodScreen.createRoute(it)) }
            )
        }

        composable(route = Screens.StatisticsScreen.route) {
            LaunchedEffect(Unit) {
                isBottomBarDisplayed.value = true
            }
            StatisticsScreen(
                viewModel = get()
            )
        }

        composable(
            route = Screens.AddMoodScreen.route,
            arguments = listOf(
                navArgument(Screens.AddMoodScreen.paramName) {
                    type = NavType.LongType
                }
            )
        ) {
            LaunchedEffect(Unit) {
                isBottomBarDisplayed.value = false
            }
            AddMoodScreen(
                emotionHistoryId = it.arguments?.getLong(Screens.AddMoodScreen.paramName) ?: -1,
                viewModel = get(),
                onBack = { navController.navigateUp() }
            )
        }
    }
}