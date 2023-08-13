package com.alexzh.moodtracker.presentation.feature.settings

import androidx.annotation.StringRes

data class SettingsScreenState(
    val isDataSuccessfullyExported: Boolean = false,
    val isDataSuccessfullyImported: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
