package com.alexzh.moodtracker.presentation.feature.addmood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.alexzh.moodtracker.R

@ExperimentalMaterial3Api
@Composable
fun AddMoodScreen(
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary),
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back_contentDescription)
                        )
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Add Mood", style = TextStyle(fontSize = 36.sp))
            }
        }
    )
}