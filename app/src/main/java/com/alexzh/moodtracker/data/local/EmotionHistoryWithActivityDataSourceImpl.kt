package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.MoodTrackerDatabase
import com.alexzh.moodtrackerdb.EmotionHistoryWithActivities
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

class EmotionHistoryWithActivityDataSourceImpl(
    db: MoodTrackerDatabase
): EmotionHistoryWithActivityDataSource {
    private val queries = db.emotionHistoryWithActivitiesQueries

    override fun getEmotionHistoryWithActivities(): Flow<List<EmotionHistoryWithActivities>> {
        return queries.getEmotionHistoryWithActivities().asFlow().mapToList()
    }

    override fun getEmotionHistoryWithActivitiesByDate(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): Flow<List<EmotionHistoryWithActivities>> {
        return queries.getEmotionHistoryWithActivitiesByDate(startDate, endDate).asFlow().mapToList()
    }
}