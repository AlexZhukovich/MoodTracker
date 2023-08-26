package com.alexzh.moodtracker.presentation.feature.settings.reminders

import java.time.DayOfWeek

data class ReminderInfoUi(
    val reminderId: Long,
    val formattedTime: String,
    val isEnabled: Boolean,
    val isExpanded: Boolean,
    val repeatDays: List<DayOfWeek>
)
