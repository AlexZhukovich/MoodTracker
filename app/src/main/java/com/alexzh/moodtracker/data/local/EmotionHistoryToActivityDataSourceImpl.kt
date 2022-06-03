package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.MoodTrackerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmotionHistoryToActivityDataSourceImpl(
    db: MoodTrackerDatabase
): EmotionHistoryToActivityDataSource {
    private val queries = db.emotionHistoryToActivityEntityQueries

    override suspend fun insertEmotionHistoryToActivity(
        emotionHistoryId: Long,
        activityId: Long,
        id: Long?
    ) {
        withContext(Dispatchers.IO) {
            queries.insert(id, emotionHistoryId, activityId)
        }
    }

    override suspend fun deleteEmotionHistoryToActivity(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }

    override suspend fun deleteByEmotionHistoryIdAndActivityId(
        emotionHistoryId: Long,
        activityId: Long
    ) {
        withContext(Dispatchers.IO) {
            queries.deleteByEmotionHistoryIdAndActivityId(emotionHistoryId, activityId)
        }
    }
}