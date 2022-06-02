package com.alexzh.moodtracker.presentation.feature.today

import com.alexzh.moodtracker.presentation.feature.today.model.MoodDataItem
import java.time.LocalDate

data class TodayScreenState(
    val isLoading: Boolean = false,
    val date: LocalDate = LocalDate.now(),
    val items: List<MoodDataItem> = emptyList(),
)
