package com.alexzh.moodtracker.presentation.feature.addmood

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.R
import com.alexzh.moodtracker.data.local.*
import com.alexzh.moodtracker.data.local.adapter.DATE_TIME_ZONE_UTC
import com.alexzh.moodtracker.presentation.core.icon.ActivityIconMapper
import com.alexzh.moodtracker.presentation.core.icon.EmotionIconMapper
import com.alexzh.moodtrackerdb.EmotionHistoryWithActivities
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

class AddMoodViewModel(
    private val emotionDataSource: EmotionDataSource,
    private val activityDataSource: ActivityDataSource,
    private val emotionHistoryDataSource: EmotionHistoryDataSource,
    private val emotionHistoryWithActivityDataSource: EmotionHistoryWithActivityDataSource,
    private val emotionHistoryToActivityDataSource: EmotionHistoryToActivityDataSource,
    private val activityIconMapper: ActivityIconMapper,
    private val emotionIconMapper: EmotionIconMapper
) : ViewModel() {
    private var existingEntity: EmotionHistoryWithActivities? = null

    private val _state = mutableStateOf(AddMoodScreenState())
    val state: State<AddMoodScreenState> = _state

    fun onEvent(event: AddMoodEvent) {
        when (event) {
            is AddMoodEvent.Load -> load(event.emotionHistoryId)
            is AddMoodEvent.OnEmotionChange -> onEmotionChange(event.emotionId)
            is AddMoodEvent.OnDateChange -> onDateChange(event.date)
            is AddMoodEvent.OnTimeChange -> onTimeChange(event.time)
            is AddMoodEvent.OnActivityChange -> onActivityChange(event.activityId)
            is AddMoodEvent.OnNoteChange -> onNodeChange(event.note)
            is AddMoodEvent.Delete -> delete()
            is AddMoodEvent.Save -> save()
        }
    }

    private fun load(emotionHistoryId: Long) {
        _state.value = _state.value.copy(
            isLoading = true,
            emotions = emptyList(),
            activities = emptyList(),
            isSaving = false,
            isSaved = false
        )
        viewModelScope.launch {
            val emotions = emotionDataSource.getEmotions()
            val activities = activityDataSource.getActivities()

            existingEntity = emotionHistoryWithActivityDataSource.getEmotionHistoryWithActivitiesById(emotionHistoryId)
            _state.value = _state.value.copy(
                isLoading = false,
                date = existingEntity?.date?.toLocalDate() ?: LocalDate.now(),
                time = existingEntity?.date?.toLocalTime() ?: LocalTime.now(),
                emotions = emotions.map {
                    emotionIconMapper.mapToSelectableEmotionItem(
                        it,
                        R.drawable.ic_question_mark,
                        existingEntity?.emotionId == it.id
                    )
                },
                activities = activities.map {
                    activityIconMapper.mapToSelectableActivityItem(
                        it,
                        R.drawable.ic_question_mark,
                        existingEntity?.activityIds?.contains(it.id.toString()) ?: false
                    )
                },
                note = if (existingEntity == null) "" else existingEntity?.note ?: ""
            )
        }
    }

    private fun onEmotionChange(id: Long) {
        _state.value = _state.value.copy(emotions = _state.value.emotions.map {
            if (it.emotionId == id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                if (it.isSelected) {
                    it.copy(isSelected = false)
                } else {
                    it
                }
            }
        })
    }

    private fun onDateChange(date: LocalDate) {
        _state.value = _state.value.copy(date = date)
    }

    private fun onTimeChange(time: LocalTime) {
        _state.value = _state.value.copy(time = time)
    }

    private fun onActivityChange(id: Long) {
        _state.value = _state.value.copy(activities = _state.value.activities.map {
            if (it.id == id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        })
    }

    private fun onNodeChange(note: String) {
        _state.value = _state.value.copy(note = note)
    }

    private fun delete() {
        // TODO: THINK WHAT DO DO WITH IT - HOW TO DIFFERENTIATE LOADING - SAVING - DELETEING
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            // TODO: DELETE IN ONE TRANSACTION
            existingEntity?.let { entity ->
                val activitiesToDelete = existingEntity?.activityIds?.split(",") ?: emptyList()
                activitiesToDelete.forEach {
                    emotionHistoryToActivityDataSource.deleteByEmotionHistoryIdAndActivityId(
                        entity.id,
                        it.toLong()
                    )
                }

                emotionHistoryDataSource.deleteEmotionHistory(entity.id)
            }
            // TODO: I NEED TO USE ANOTHER STATE PROP INSTEAD OF "isSaved"
            _state.value = _state.value.copy(isLoading = false, isSaved = true)
        }
    }

    private fun save() {
        _state.value = _state.value.copy(isSaving = true)

        viewModelScope.launch {
            // TODO: INSERT IT INTO SINGLE TRANSACTION IN REPOSITORY
            emotionHistoryDataSource.insertEmotionHistory(
                date = ZonedDateTime.of(_state.value.date, _state.value.time, DATE_TIME_ZONE_UTC),
                emotionId = _state.value.emotions.first { it.isSelected }.emotionId,
                note = _state.value.note.ifEmpty { null },
                id = existingEntity?.id
            )
            val newId = emotionHistoryDataSource.getLastInsertedRowId()
            // TODO: CHECK WHAT IT RETURNS FOR EMPTY DATABASE
            if (newId != null) {
                existingEntity?.let { emotionHistoryWithActivities ->
                    val activitiesToDelete = existingEntity?.activityIds?.split(",") ?: emptyList()

                    activitiesToDelete.forEach {
                        emotionHistoryToActivityDataSource.deleteByEmotionHistoryIdAndActivityId(
                            emotionHistoryWithActivities.id,
                            it.toLong()
                        )
                    }
                }

                _state.value.activities.filter { it.isSelected }.forEach { activity ->
                    emotionHistoryToActivityDataSource.insertEmotionHistoryToActivity(
                        newId,
                        activity.id
                    )
                }
                _state.value = AddMoodScreenState(isSaving = false, isSaved = true)
            }
        }
    }
}