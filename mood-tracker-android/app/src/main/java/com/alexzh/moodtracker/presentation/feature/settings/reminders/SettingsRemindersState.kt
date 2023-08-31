package com.alexzh.moodtracker.presentation.feature.settings.reminders

data class SettingsRemindersState(
    val isLoading: Boolean = false,
    val reminders: List<ReminderInfoUi> = emptyList(),
    val permissionIsGranted: Boolean = true,
    val showAddReminderDialog: Boolean = false,
    val showDeleteReminderDialog: Boolean = false
)
