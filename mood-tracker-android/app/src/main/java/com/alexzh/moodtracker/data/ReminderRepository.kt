package com.alexzh.moodtracker.data

import com.alexzh.moodtrackerdb.ReminderEntity
import kotlinx.coroutines.flow.Flow
import com.alexzh.moodtracker.data.util.Result
import java.time.DayOfWeek
import java.time.LocalTime


interface ReminderRepository {

    fun getAllReminders(): Flow<Result<List<ReminderEntity>>>

    suspend fun getReminder(id: Long): ReminderEntity?

    suspend fun insertReminder(
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean
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