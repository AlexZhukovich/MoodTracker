package com.alexzh.moodtracker.data.local

interface EmotionHistoryToActivityDataSource {

    suspend fun insertEmotionHistoryToActivity(emotionHistoryId: Long, activityId: Long, id: Long? = null)

    suspend fun deleteEmotionHistoryToActivity(id: Long)
}