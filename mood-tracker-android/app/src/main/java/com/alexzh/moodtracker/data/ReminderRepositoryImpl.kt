package com.alexzh.moodtracker.data

import com.alexzh.moodtracker.data.local.LocalRemindersDataSource
import com.alexzh.moodtrackerdb.ReminderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.alexzh.moodtracker.data.util.Result
import java.time.DayOfWeek
import java.time.LocalTime

class ReminderRepositoryImpl(
    private val localDataSource: LocalRemindersDataSource,
) : ReminderRepository {

    override fun getAllReminders(): Flow<Result<List<ReminderEntity>>> {
        return flow {
            emit(Result.Loading())
            emit(Result.Success(localDataSource.getAllReminders()))
        }
    }

    override suspend fun getReminder(id: Long): ReminderEntity? {
        return localDataSource.getReminder(id)
    }

    override suspend fun insertReminder(
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean
    ): Long {
        return localDataSource.insertReminder(
            time = time,
            repeatDays = repeatDays,
            isEnabled = isEnabled
        )
    }

    override suspend fun updateReminder(
        id: Long,
        time: LocalTime,
        repeatDays: List<DayOfWeek>,
        isEnabled: Boolean
    ) {
        localDataSource.updateReminder(
            id = id,
            time = time,
            repeatDays = repeatDays,
            isEnabled = isEnabled
        )
    }

    override suspend fun deleteReminder(id: Long) {
        localDataSource.deleteReminder(id)
    }
}