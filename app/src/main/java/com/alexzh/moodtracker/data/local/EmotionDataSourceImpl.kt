package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtrackerdb.EmotionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmotionDataSourceImpl(
    db: MoodTrackerDatabase
): EmotionDataSource {
    private val queries = db.emotionEntityQueries

    override suspend fun getEmotionById(id: Long): EmotionEntity? {
        return withContext(Dispatchers.IO) {
            queries.getEmotionById(id).executeAsOneOrNull()
        }
    }

    override suspend fun getEmotions(): List<EmotionEntity> {
        return queries.getEmotions().executeAsList()
    }

    override suspend fun insertEmotion(name: String, happinessLevel: Int, icon: String, id: Long?) {
        withContext(Dispatchers.IO) {
            queries.insert(id, name, happinessLevel, icon)
        }
    }

    override suspend fun deleteAction(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }
}