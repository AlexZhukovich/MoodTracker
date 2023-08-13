package com.alexzh.moodtracker.presentation.feature.settings.backup

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.DataManagerRepository
import com.alexzh.moodtracker.data.model.backup.ExportDataStatus
import com.alexzh.moodtracker.data.model.backup.ImportDataStatus
import com.alexzh.moodtracker.data.util.convertToString
import com.alexzh.moodtracker.presentation.feature.settings.SettingsEvent
import com.alexzh.moodtracker.presentation.feature.settings.SettingsScreenState
import kotlinx.coroutines.launch

class SettingsImportAndExportViewModel(
    private val dataManagerRepository: DataManagerRepository,
    private val application: Application
): AndroidViewModel(application) {
    private val _state = mutableStateOf(SettingsScreenState())
    val state: State<SettingsScreenState> = _state

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ExportData -> exportData(event.selectedDirectoryUri)
            is SettingsEvent.ImportData -> importData(event.selectedFileUri)
        }
    }

    private fun exportData(selectedDirectoryUri: Uri?) {
        selectedDirectoryUri?: return

        viewModelScope.launch {
            when (dataManagerRepository.exportUserData(selectedDirectoryUri)) {
                is ExportDataStatus.NoData -> {
                    _state.value = SettingsScreenState(
                        errorMessage = R.string.settingsImportAndExportScreen_noDataToExport_message
                    )
                }
                is ExportDataStatus.DataSuccessfullyExported -> {
                    _state.value = SettingsScreenState(isDataSuccessfullyExported = true)
                }
            }
        }
    }

    private fun importData(selectedFileUri: Uri?) {
        selectedFileUri ?: return

        viewModelScope.launch {
            val fileStream = application.contentResolver.openInputStream(selectedFileUri)
            val jsonStr = fileStream.use {
                fileStream?.convertToString() ?: ""
            }

            when (dataManagerRepository.importData(jsonStr)) {
                is ImportDataStatus.NoData -> {
                    _state.value = SettingsScreenState(
                        errorMessage = R.string.settingsImportAndExportScreen_noDataToImport_message
                    )
                }
                is ImportDataStatus.DataSuccessfullyImported -> {
                    _state.value = SettingsScreenState(isDataSuccessfullyImported = true)
                }
            }
        }
    }
}