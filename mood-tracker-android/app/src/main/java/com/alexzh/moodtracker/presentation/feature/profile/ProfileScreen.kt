package com.alexzh.moodtracker.presentation.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R

@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary),
                title = { Text(stringResource(R.string.profileScreen_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back_contentDescription)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    start = 8.dp,
                    top = paddingValues.calculateTopPadding(),
                    end = 8.dp,
                    bottom = paddingValues.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(ProfileEvent.CreateAccount) }
            ) {
                Text("CREATE AN ACCOUNT")
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(ProfileEvent.LogIn) }
            ) {
                Text("LOGIN")
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(ProfileEvent.GetUserInfo) }
            ) {
                Text("GET USER INFORMATION")
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(ProfileEvent.LogOut) }
            ) {
                Text("LOGOUT")
            }
        }
    }
}