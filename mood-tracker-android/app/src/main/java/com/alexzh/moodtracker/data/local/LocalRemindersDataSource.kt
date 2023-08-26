package com.alexzh.moodtracker.data.local

import com.alexzh.moodtrackerdb.ReminderEntity
import java.time.DayOfWeek
import java.time.LocalTime

interface LocalRemindersDataSource {

    suspend fun getAllReminders(): List<ReminderEntity>

    suspend fun getReminder(id: Long): ReminderEntity?

    suspend fun insertReminder(
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean,
    ): Long

    suspend fun updateReminder(
        id: Long,
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean
    )

    suspend fun deleteReminder(
        id: Long
    )
}