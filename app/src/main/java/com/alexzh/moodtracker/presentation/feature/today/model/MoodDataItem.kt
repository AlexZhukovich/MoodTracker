package com.alexzh.moodtracker.presentation.feature.today.model

import androidx.annotation.DrawableRes

data class MoodDataItem(
    val note: String?,
    @DrawableRes val iconId: Int,
    val formattedDate: String,
    val activities: List<String>
)