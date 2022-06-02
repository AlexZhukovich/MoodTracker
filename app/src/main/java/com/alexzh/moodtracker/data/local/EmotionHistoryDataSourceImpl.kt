package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtrackerdb.EmotionHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime

class EmotionHistoryDataSourceImpl(
    db: MoodTrackerDatabase
): EmotionHistoryDataSource {
    private val queries = db.emotionHistoryEntityQueries

    override suspend fun getLastInsertedRowId(): Long? {
        return withContext(Dispatchers.IO) {
            queries.getLastInsertedRowId().executeAsOneOrNull()
        }
    }

    override suspend fun getEmotionHistoryById(id: Long): EmotionHistoryEntity? {
        return withContext(Dispatchers.IO) {
            queries.getEmotionHistoryById(id).executeAsOneOrNull()
        }
    }

    override suspend fun insertEmotionHistory(
        date: ZonedDateTime,
        emotionId: Long,
        note: String?,
        id: Long?
    ) {
        withContext(Dispatchers.IO) {
            queries.insert(id, date, emotionId, note)
        }
    }

    override suspend fun deleteEmotionHistory(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }
}