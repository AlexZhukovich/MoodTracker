package com.alexzh.moodtracker.presentation.feature.today.model

import androidx.annotation.DrawableRes
import com.alexzh.moodtracker.presentation.core.ActivityItem

data class EmotionHistoryItem(
    val id: Long,
    val note: String?,
    @DrawableRes val iconId: Int,
    val formattedDate: String,
    val activities: List<ActivityItem>
)