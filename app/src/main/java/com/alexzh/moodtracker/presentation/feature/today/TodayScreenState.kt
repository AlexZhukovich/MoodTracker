package com.alexzh.moodtracker.presentation.feature.today

import com.alexzh.moodtracker.presentation.feature.today.model.MoodDataItem

sealed class TodayScreenState {
    object Loading: TodayScreenState()
    data class Success(val data: List<MoodDataItem>): TodayScreenState()
}
