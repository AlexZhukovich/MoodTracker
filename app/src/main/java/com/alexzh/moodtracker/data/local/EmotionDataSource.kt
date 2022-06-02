package com.alexzh.moodtracker.data.local

import com.alexzh.moodtrackerdb.EmotionEntity

interface EmotionDataSource {

    suspend fun getEmotionById(id: Long): EmotionEntity?

    suspend fun getEmotions(): List<EmotionEntity>

    suspend fun insertEmotion(name: String, happinessLevel: Int, icon: String, id: Long? = null)

    suspend fun deleteAction(id: Long)
}