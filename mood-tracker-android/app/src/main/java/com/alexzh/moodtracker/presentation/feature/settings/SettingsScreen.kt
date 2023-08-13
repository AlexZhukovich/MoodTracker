package com.alexzh.moodtracker.presentation.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.design.component.settings.SettingsItem

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    onImportAndExport: () -> Unit,
    onProfile: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settingsScreen_title)) },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary)
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 0.dp,
                top = paddingValues.calculateTopPadding(),
                end = 0.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            SettingsItem(
                title = R.string.settingsScreen_importAndExport_title,
                subtitle = R.string.settingsScreen_importAndExport_subtitle,
                icon = R.drawable.ic_import_and_export,
                contentDescription = R.string.settingsScreen_importAndExport_contentDescription,
                onClick = onImportAndExport
            )
            SettingsItem(
                title = R.string.settingsScreen_profile_title,
                subtitle = R.string.settingsScreen_profile_subtitle,
                icon = R.drawable.ic_settings_account,
                contentDescription = R.string.settingsScreen_profile_contentDescription,
                onClick = { onProfile() }
            )
        }
    }
}