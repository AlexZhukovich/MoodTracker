package com.alexzh.moodtracker.data.local

import com.alexzh.moodtracker.data.model.Activity
import com.alexzh.moodtracker.data.model.Emotion
import com.alexzh.moodtracker.data.model.EmotionHistory
import com.alexzh.moodtrackerdb.DayToAverageHappinessLevel
import java.time.ZonedDateTime

interface LocalEmotionHistoryDataSource {

    // TODO: ADD PARAMS
    suspend fun getDayToAverageHappinessLevel(): List<DayToAverageHappinessLevel>

    suspend fun getEmotionsHistoryByDate(
        startDate: ZonedDateTime,
        endDate: ZonedDateTime
    ): List<EmotionHistory>

    suspend fun getEmotionHistoryById(
        id: Long
    ): EmotionHistory?

    suspend fun getEmotions(): List<Emotion>

    suspend fun getActivities(): List<Activity>

    suspend fun insertOrUpdateEmotionHistory(
        date: ZonedDateTime,
        emotionId: Long,
        selectedActivityIds: List<Long>,
        note: String?,
        id: Long? = null
    )

    suspend fun deleteEmotionHistory(
        emotionHistory: EmotionHistory
    )
}