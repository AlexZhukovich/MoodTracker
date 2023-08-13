package com.alexzh.moodtracker.data

import android.app.Application
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtracker.data.local.LocalEmotionHistoryDataSource
import com.alexzh.moodtracker.data.model.backup.BackupData
import com.alexzh.moodtracker.data.model.backup.ExportDataStatus
import com.alexzh.moodtracker.data.model.backup.ImportDataStatus
import com.alexzh.moodtracker.data.util.ZonedDateTimeGsonTypeAdapter
import com.google.gson.GsonBuilder
import java.time.ZonedDateTime

class DataManagerRepositoryImpl(
    private val localEmotionHistoryDataSource: LocalEmotionHistoryDataSource,
    private val emotionHistoryRepository: EmotionHistoryRepository,
    private val application: Application
) : DataManagerRepository {
    override suspend fun exportUserData(selectedDirectoryUri: Uri): ExportDataStatus {
        val emotionHistoryList = localEmotionHistoryDataSource.getAllEmotionHistory()
        if (emotionHistoryList.isEmpty()) {
            return ExportDataStatus.NoData
        }

        val json = GsonBuilder().setPrettyPrinting().create()
            .toJson(BackupData(MoodTrackerDatabase.Schema.version, emotionHistoryList))
        val fileName = "moodTracker_export_${System.currentTimeMillis()}.json"

        val directory = DocumentFile.fromTreeUri(application, selectedDirectoryUri)
        var docFile = directory?.findFile(fileName)
        if (docFile == null) {
            docFile =
                DocumentFile.fromTreeUri(application, selectedDirectoryUri)?.createFile(
                    "*/*",
                    fileName
                )
        }

        docFile?.uri?.let { fileUri ->
            val fileStream = application.contentResolver.openOutputStream(fileUri)
            fileStream?.write(json.toString().toByteArray())
            fileStream?.flush()
            fileStream?.close()
        }
        return ExportDataStatus.DataSuccessfullyExported
    }

    override suspend fun importData(jsonStr: String): ImportDataStatus {
        val backupData = GsonBuilder()
            .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeGsonTypeAdapter())
            .create()
            .fromJson(jsonStr, BackupData::class.java)

        if (backupData.list.isEmpty()) {
            return ImportDataStatus.NoData
        }

        backupData.list.forEach { emotionHistory ->
            emotionHistoryRepository.insertOrUpdateEmotionHistory(
                id = null,
                date = emotionHistory.date,
                emotionId = emotionHistory.emotion.id,
                selectedActivityIds = emotionHistory.activities.map { it.id },
                note = emotionHistory.note
            )
        }
        return ImportDataStatus.DataSuccessfullyImported
    }
}