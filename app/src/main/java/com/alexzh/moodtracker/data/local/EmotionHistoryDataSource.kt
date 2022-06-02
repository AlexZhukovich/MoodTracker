package com.alexzh.moodtracker.data.local

import com.alexzh.moodtrackerdb.EmotionHistoryEntity
import java.time.ZonedDateTime

interface EmotionHistoryDataSource {

    suspend fun getLastInsertedRowId(): Long?

    suspend fun getEmotionHistoryById(id: Long): EmotionHistoryEntity?

    suspend fun insertEmotionHistory(date: ZonedDateTime, emotionId: Long, note: String? = null, id: Long? = null)

    suspend fun deleteEmotionHistory(id: Long)
}