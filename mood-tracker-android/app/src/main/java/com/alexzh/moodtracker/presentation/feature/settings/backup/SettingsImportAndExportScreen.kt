package com.alexzh.moodtracker.presentation.feature.settings.backup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.design.component.settings.SettingsItem
import com.alexzh.moodtracker.presentation.feature.settings.SettingsEvent

@ExperimentalMaterial3Api
@Composable
fun SettingsImportAndExportScreen(
    viewModel: SettingsImportAndExportViewModel,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.state

    val backupDirectoryPicker = rememberLauncherForActivityResult(
        contract = BackupDirectoryPickContract(),
        onResult = { uri ->
            viewModel.onEvent(SettingsEvent.ExportData(uri))
        }
    )

    val restoreFilePicker = rememberLauncherForActivityResult(
        contract = RestoreBackupFileContract(),
        onResult = { uri ->
            viewModel.onEvent(SettingsEvent.ImportData(uri))
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settingsImportAndExportScreen_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back_contentDescription)
                        )
                    }
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.inversePrimary)
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { Snackbar(it) }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isDataSuccessfullyExported -> {
                val text = stringResource(id = R.string.settingsImportAndExportScreen_export_dataSuccessfullyExported_message)
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(text)
                }
            }
            uiState.isDataSuccessfullyImported -> {
                val text = stringResource(id = R.string.settingsImportAndExportScreen_import_dataSuccessfullyImported_message)
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(text)
                }
            }
            // TODO: THINK HOW TO IMPROVE IT AND AVOID "!!"
            uiState.errorMessage != null -> {
                val text = stringResource(uiState.errorMessage!!)
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar(text)
                }
            }
        }

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
                title = R.string.settingsImportAndExportScreen_export_title,
                subtitle = R.string.settingsImportAndExportScreen_export_subtitle,
                icon = R.drawable.ic_export,
                contentDescription = R.string.settingsImportAndExportScreen_export_contentDescription,
                onClick = {
                    backupDirectoryPicker.launch(arrayOf())
                }
            )
            SettingsItem(
                title = R.string.settingsImportAndExportScreen_import_title,
                subtitle = R.string.settingsImportAndExportScreen_import_subtitle,
                icon = R.drawable.ic_import,
                contentDescription = R.string.settingsImportAndExportScreen_import_contentDescription,
                onClick = {
                    restoreFilePicker.launch(arrayOf())
                }
            )
        }
    }
}
