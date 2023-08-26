package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtrackerdb.ReminderEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalTime

class LocalRemindersDataSourceImpl(
    private val db: MoodTrackerDatabase,
    private val dispatcher: CoroutineDispatcher
): LocalRemindersDataSource {
    private val reminderEntityQueries = db.reminderEntityQueries

    override suspend fun getAllReminders(): List<ReminderEntity> {
        return withContext(dispatcher) {
            reminderEntityQueries
                .getReminders()
                .executeAsList()
        }
    }

    override suspend fun getReminder(id: Long): ReminderEntity? {
        return withContext(dispatcher) {
            reminderEntityQueries
                .getReminder(id)
                .executeAsOneOrNull()
        }
    }

    override suspend fun insertReminder(
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean
    ): Long {
        return withContext(dispatcher) {
            db.transaction {
                reminderEntityQueries.insert(
                    time = time,
                    repeatDays = repeatDays,
                    isEnabled = isEnabled,
                    id = null
                )
            }
            reminderEntityQueries.lastInsertRowId().executeAsOne()
        }
    }

    override suspend fun updateReminder(
        id: Long,
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean
    ) {
        return withContext(dispatcher) {
            db.transaction {
                reminderEntityQueries.insert(
                    time = time,
                    repeatDays = repeatDays,
                    isEnabled = isEnabled,
                    id = id
                )
            }
            reminderEntityQueries.lastInsertRowId().executeAsOne()
        }
    }

    override suspend fun deleteReminder(id: Long) {
        withContext(dispatcher) {
            db.transaction {
                reminderEntityQueries.delete(id)
            }
        }
    }
}