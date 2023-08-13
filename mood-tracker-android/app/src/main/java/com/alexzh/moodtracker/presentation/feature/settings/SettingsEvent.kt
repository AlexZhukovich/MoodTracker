package com.alexzh.moodtracker.presentation.feature.settings

import android.net.Uri

sealed class SettingsEvent {
    class ExportData(val selectedDirectoryUri: Uri?): SettingsEvent()

    class ImportData(val selectedFileUri: Uri?): SettingsEvent()
}