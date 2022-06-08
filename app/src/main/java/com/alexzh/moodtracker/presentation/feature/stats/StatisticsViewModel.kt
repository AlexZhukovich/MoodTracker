package com.alexzh.moodtracker.presentation.feature.stats

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.data.EmotionHistoryRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatisticsViewModel(
    private val emotionHistoryRepository: EmotionHistoryRepository
): ViewModel() {

    private val _state = mutableStateOf(StatisticsScreenState())
    val state: State<StatisticsScreenState> = _state

    fun onEvent(event: StatisticsEvent) {
        when (event) {
            is StatisticsEvent.Load -> load()
        }
    }

    private fun load() {
        _state.value = _state.value.copy(loading = true, data = emptyList())
        viewModelScope.launch {
            val currentDate = LocalDate.now()
            val currentWeekData = emotionHistoryRepository.getDayToAverageHappinessLevel()
                .map { it.date.toLocalDate() to it.happinessLevel }
                .groupBy { it.first }.mapValues { entity ->
                    entity.value.sumOf { it.second.toDouble() } / entity.value.size
                }
                .filter { it.key >= currentDate.minusDays(6) && it.key <= currentDate }
                .toMutableMap()

            if (currentWeekData.isEmpty()) {
                _state.value = _state.value.copy(
                    loading = false,
                    data = emptyList()
                )
            } else {

                (6L downTo 0L).forEach {
                    if (!currentWeekData.containsKey(LocalDate.now().minusDays(it.toLong()))) {
                        currentWeekData[LocalDate.now().minusDays(it)] = 0.toDouble()
                    }
                }

                _state.value = _state.value.copy(
                    loading = false,
                    data = currentWeekData.toList().sortedBy { it.first }
                )
            }
        }
    }
}