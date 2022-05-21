package com.alexzh.moodtracker.presentation.feature.today

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.presentation.core.date.TimeFormatter
import com.alexzh.moodtracker.presentation.core.icon.IconMapper
import com.alexzh.moodtracker.presentation.feature.today.model.MoodDataItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class TodayViewModel(
    private val iconMapper: IconMapper,
    private val timeFormatter: TimeFormatter
) : ViewModel() {
    private val _uiState = mutableStateOf<TodayScreenState>(TodayScreenState.Loading)
    val uiState: State<TodayScreenState> = _uiState

    fun loadMood(date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            _uiState.value = TodayScreenState.Loading

            delay(1_000)

            _uiState.value = TodayScreenState.Success(
                data = listOf(
                    MoodDataItem(
                        note = "Test note",
                        iconId = iconMapper.getIconResId(5, R.drawable.ic_question_mark),
                        formattedDate = timeFormatter.format(LocalDateTime.now()),
                        activities = listOf("Shopping", "Shopping 2")
                    ),
                    MoodDataItem(
                        note = null,
                        iconId = iconMapper.getIconResId(4, R.drawable.ic_question_mark),
                        formattedDate = timeFormatter.format(LocalDateTime.now()),
                        activities = listOf("Walking")
                    ),
                    MoodDataItem(
                        note = "Test note Test note Test note Test note Test note Test note Test note Test note Test note Test note",
                        iconId = iconMapper.getIconResId(3, R.drawable.ic_question_mark),
                        formattedDate = timeFormatter.format(LocalDateTime.now()),
                        activities = listOf("Working", "Working", "Working", "Working", "Working", "Working", "Working")
                    ),
                    MoodDataItem(
                        note = null,
                        iconId = iconMapper.getIconResId(2, R.drawable.ic_question_mark),
                        formattedDate = timeFormatter.format(LocalDateTime.now()),
                        activities = listOf("Cleaning the house")
                    ),
                    MoodDataItem(
                        note = null,
                        iconId = iconMapper.getIconResId(1, R.drawable.ic_question_mark),
                        formattedDate = timeFormatter.format(LocalDateTime.now()),
                        activities = listOf("Failed exam")
                    )
                )
            )
        }
    }
}