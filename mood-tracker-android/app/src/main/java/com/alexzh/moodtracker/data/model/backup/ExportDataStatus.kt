package com.alexzh.moodtracker.data.model.backup

sealed class ExportDataStatus {
    object NoData : ExportDataStatus()
    object DataSuccessfullyExported : ExportDataStatus()
}