package com.alexzh.moodtracker.data.local

import com.alexzh.moodtrackerdb.EmotionHistoryWithActivities
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface EmotionHistoryWithActivityDataSource {

    fun getEmotionHistoryWithActivities(): Flow<List<EmotionHistoryWithActivities>>

    fun getEmotionHistoryWithActivitiesByDate(startDate: ZonedDateTime, endDate: ZonedDateTime): Flow<List<EmotionHistoryWithActivities>>
}