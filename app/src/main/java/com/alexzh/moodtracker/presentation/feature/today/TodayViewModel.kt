package com.alexzh.moodtracker.presentation.feature.today

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.local.ActivityDataSource
import com.alexzh.moodtracker.data.local.EmotionHistoryWithActivityDataSource
import com.alexzh.moodtracker.data.local.adapter.DATE_TIME_ZONE_UTC
import com.alexzh.moodtracker.presentation.core.icon.ActivityIconMapper
import com.alexzh.moodtracker.presentation.core.icon.EmotionIconMapper
import com.alexzh.moodtracker.presentation.feature.today.model.MoodDataItem
import com.alexzh.moodtrackerdb.ActivityEntity
import com.alexzh.moodtrackerdb.EmotionEntity
import com.alexzh.moodtrackerdb.EmotionHistoryWithActivities
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class TodayViewModel(
    private val activityDataSource: ActivityDataSource,
    private val emotionHistoryWithActivityDataSource: EmotionHistoryWithActivityDataSource,
    private val emotionIconMapper: EmotionIconMapper,
    private val activityIconMapper: ActivityIconMapper,
) : ViewModel() {
    private val _uiState = mutableStateOf(TodayScreenState())
    val uiState: State<TodayScreenState> = _uiState

    init {
        fetchMoodHistory(_uiState.value.date)
    }

    fun onEvent(event: TodayEvent) {
        when (event) {
            is TodayEvent.OnDateChange -> fetchMoodHistory(event.date)
        }
    }

    // TODO: ADD SUPPORT FOR 12 AND 24 HOURS FORMATS
    private fun fetchMoodHistory(date: LocalDate) {
        _uiState.value = _uiState.value.copy(isLoading = true, items = emptyList())
        viewModelScope.launch {
            launch {
                val startDate = ZonedDateTime.of(date, LocalTime.of(0,0), DATE_TIME_ZONE_UTC)
                val endDate = ZonedDateTime.of(date, LocalTime.of(23,59), DATE_TIME_ZONE_UTC)

                val activities = activityDataSource.getActivities()
                emotionHistoryWithActivityDataSource.getEmotionHistoryWithActivitiesByDate(startDate, endDate).collect {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        items = mapToMoodDataItem(it, activities)
                    )
                }
            }
        }
    }

    private fun mapToMoodDataItem(
        data: List<EmotionHistoryWithActivities>,
        activities: List<ActivityEntity>
    ): List<MoodDataItem> {
        return data.map { item ->
            MoodDataItem(
                id = item.id,
                note = item.note,
                iconId = emotionIconMapper.mapToEmotionItem(
                    EmotionEntity(
                        item.emotionId,
                        item.emotionName,
                        item.emotionHappinessLevel,
                        item.emotionIcon
                    ),
                    R.drawable.ic_question_mark
                ).iconRes,
                formattedDate = item.date.format(DateTimeFormatter.ofPattern("HH:mm")),
                activities = item.activityIds.split(",").map { activityId ->
                    activityIconMapper.mapToActivityItem(
                        requireNotNull(activities.find { it.id == activityId.toLong()}),
                        R.drawable.ic_question_mark
                    )
                }
            )
        }
    }
}