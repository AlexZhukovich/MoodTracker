package com.alexzh.moodtracker.presentation.feature.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import com.alexzh.moodtracker.presentation.navigation.AppNavigation
import com.alexzh.moodtracker.presentation.theme.AppTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            AppTheme {
                AppNavigation(navController)
            }
        }
    }
}