package com.alexzh.moodtracker.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.alexzh.moodtracker.presentation.feature.addmood.AddMoodScreen
import com.alexzh.moodtracker.presentation.feature.today.TodayScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import org.koin.androidx.compose.get

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun AppNavigation(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screens.TodayScreen.route,
        enterTransition = { expandIn(animationSpec = tween(800)) },
        exitTransition = { shrinkOut(animationSpec = tween(800)) }
    ) {
        composable(
            route = Screens.TodayScreen.route,
            enterTransition = {
                if (initialState.destination.route == Screens.AddMoodScreen.route) {
                    slideIntoContainer(
                        AnimatedContentScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                } else null
            },
            exitTransition = {
                if (targetState.destination.route == Screens.AddMoodScreen.route) {
                    slideOutOfContainer(
                        AnimatedContentScope.SlideDirection.Left,
                        animationSpec = tween(600)
                    )
                } else null
            }
        ) {
            TodayScreen(
                viewModel = get(),
                onAdd = { navController.navigate(Screens.AddMoodScreen.route) }
            )
        }

        composable(Screens.AddMoodScreen.route,
            enterTransition = {
                if (initialState.destination.route == Screens.TodayScreen.route) slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(600)
                )
                else null
            },
            exitTransition = {
                if (targetState.destination.route == Screens.TodayScreen.route) slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(600)
                )
                else null
            }
        ) {
            AddMoodScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}