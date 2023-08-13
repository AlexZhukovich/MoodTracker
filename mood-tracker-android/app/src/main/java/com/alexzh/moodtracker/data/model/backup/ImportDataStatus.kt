package com.alexzh.moodtracker.data.model.backup

sealed class ImportDataStatus {
    object NoData : ImportDataStatus()
    object DataSuccessfullyImported : ImportDataStatus()
}