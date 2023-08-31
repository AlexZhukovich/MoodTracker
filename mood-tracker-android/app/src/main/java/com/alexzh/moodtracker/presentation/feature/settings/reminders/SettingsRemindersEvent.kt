package com.alexzh.moodtracker.presentation.feature.settings.reminders

import java.time.DayOfWeek
import java.time.LocalTime

sealed class SettingsRemindersEvent {
    data object NotificationPermissionsDenied: SettingsRemindersEvent()
    data object NotificationPermissionsGrated: SettingsRemindersEvent()
    data object ShowAddReminderDialog: SettingsRemindersEvent()
    data object HideAddReminderDialog: SettingsRemindersEvent()
    data class AddReminder(val time: LocalTime): SettingsRemindersEvent()
    data class OnRepeatDaysChange(
        val reminderInfo: ReminderInfoUi,
        val day: DayOfWeek
    ): SettingsRemindersEvent()
    data class OnExpandCollapse(val reminderInfo: ReminderInfoUi): SettingsRemindersEvent()
    data class OnEnabledDisabled(val reminderInfo: ReminderInfoUi): SettingsRemindersEvent()
    data class ShowDeleteReminderDialog(val reminderInfo: ReminderInfoUi): SettingsRemindersEvent()
    data object HideDeleteReminderDialog: SettingsRemindersEvent()
    data object DeleteReminder: SettingsRemindersEvent()
}