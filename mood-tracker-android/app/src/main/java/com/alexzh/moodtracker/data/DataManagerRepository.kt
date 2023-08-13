package com.alexzh.moodtracker.data

import android.net.Uri
import com.alexzh.moodtracker.data.model.backup.ExportDataStatus
import com.alexzh.moodtracker.data.model.backup.ImportDataStatus

interface DataManagerRepository {

    suspend fun exportUserData(selectedDirectoryUri: Uri): ExportDataStatus

    suspend fun importData(jsonStr: String): ImportDataStatus
}