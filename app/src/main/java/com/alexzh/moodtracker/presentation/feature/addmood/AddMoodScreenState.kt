package com.alexzh.moodtracker.presentation.feature.addmood

import com.alexzh.moodtracker.presentation.core.SelectableActivityItem
import com.alexzh.moodtracker.presentation.core.SelectableEmotionItem
import java.time.LocalDate
import java.time.LocalTime

data class AddMoodScreenState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val emotions: List<SelectableEmotionItem> = emptyList(),
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val activities: List<SelectableActivityItem> = emptyList(),
    val note: String = ""
)