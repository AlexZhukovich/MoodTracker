package com.alexzh.moodtracker.presentation.feature.settings.reminders

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexzh.moodtracker.common.ext.indexesOf
import com.alexzh.moodtracker.data.ReminderRepository
import com.alexzh.moodtracker.data.util.Result
import com.alexzh.moodtrackerdb.ReminderEntity
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SettingsRemindersViewModel(
    private val reminderRepository: ReminderRepository
): ViewModel() {
    companion object {
        private const val INVALID_REMINDER_ID = 0L
    }

    private val _uiState = mutableStateOf(SettingsRemindersState())
    val uiState: State<SettingsRemindersState> = _uiState

    private var recentlyEditedReminderId = INVALID_REMINDER_ID
    private var reminderToDelete: ReminderInfoUi? = null

    init {
        fetchReminders()
    }

    fun onEvent(event: SettingsRemindersEvent) {
        when (event) {
            is SettingsRemindersEvent.ShowAddReminderDialog -> onShowAddReminderDialog()
            is SettingsRemindersEvent.HideAddReminderDialog -> onHideAddReminderDialog()
            is SettingsRemindersEvent.AddReminder -> onAddReminder(event.time)
            is SettingsRemindersEvent.ShowDeleteReminderDialog -> onShowDeleteReminderDialog(event.reminderInfo)
            is SettingsRemindersEvent.HideDeleteReminderDialog -> onHideDeleteReminderDialog()
            is SettingsRemindersEvent.DeleteReminder -> onDeleteReminder()
            is SettingsRemindersEvent.OnRepeatDaysChange -> onRepeatDayChange(event.reminderInfo, event.day)
            is SettingsRemindersEvent.OnExpandCollapse -> onExpandCollapse(event.reminderInfo)
            is SettingsRemindersEvent.OnEnabledDisabled -> onEnabledDisabled(event.reminderInfo)
        }
    }

    private fun onShowAddReminderDialog() {
        _uiState.value = _uiState.value.copy(showAddReminderDialog = true)
    }

    private fun onHideAddReminderDialog() {
        _uiState.value = _uiState.value.copy(showAddReminderDialog = false)
    }

    private fun fetchReminders() {
        _uiState.value = _uiState.value.copy(isLoading = true, reminders = emptyList())

        viewModelScope.launch {
            reminderRepository.getAllReminders()
                .stateIn(viewModelScope)
                .collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            reminders = mapToReminderInfoUi(result.data, recentlyEditedReminderId)
                        )
                    }
                    is Result.Error -> {
                        // TODO: IMPLEMENT IT
                    }
                }
            }
        }
    }

    // TODO: FIX IT
    private fun onAddReminder(time: LocalTime) {
        _uiState.value = _uiState.value.copy(showAddReminderDialog = false)
        viewModelScope.launch {
            val createdReminderId = reminderRepository.insertReminder(
                time = time,
                repeatDays = emptyList(),
                isEnabled = true
            )
            recentlyEditedReminderId = createdReminderId
        }
        fetchReminders()
    }

    private fun onShowDeleteReminderDialog(reminderInfo: ReminderInfoUi) {
        reminderToDelete = reminderInfo
        _uiState.value = _uiState.value.copy(showDeleteReminderDialog = true)
    }

    private fun onHideDeleteReminderDialog() {
        reminderToDelete = null
        _uiState.value = _uiState.value.copy(showDeleteReminderDialog = false)
    }

    private fun onRepeatDayChange(reminderInfo: ReminderInfoUi, day: DayOfWeek) {
        val newReminderList = _uiState.value.reminders.toMutableList()
        val indexOfUpdatedReminder = newReminderList.indexOf(reminderInfo)
        val newRepeatDays = reminderInfo.repeatDays.toMutableList()

        if (reminderInfo.repeatDays.contains(day)) {
            newRepeatDays.remove(day)
        } else {
            newRepeatDays.add(day)
        }

        viewModelScope.launch {
            reminderRepository.updateReminder(
                time = LocalTime.parse(reminderInfo.formattedTime, DateTimeFormatter.ofPattern("HH:mm")),
                repeatDays = newRepeatDays,
                isEnabled = reminderInfo.isEnabled,
                id = reminderInfo.reminderId
            )

            // TODO: FIX IT
            val updatedReminder = mapToReminderInfoUi(
                listOf(reminderRepository.getReminder(reminderInfo.reminderId)!!),
                recentlyEditedReminderId
            ).first()

            newReminderList[indexOfUpdatedReminder] = updatedReminder
            _uiState.value = _uiState.value.copy(reminders = newReminderList)
        }
    }

    private fun onDeleteReminder() {
        reminderToDelete?.let {
            val newReminderList = _uiState.value.reminders.toMutableList()
            val indexOfDeletedReminder = newReminderList.indexOf(reminderToDelete)

            viewModelScope.launch {
                reminderRepository.deleteReminder(it.reminderId)

                newReminderList.removeAt(indexOfDeletedReminder)
                _uiState.value = _uiState.value.copy(
                    showDeleteReminderDialog = false,
                    reminders = newReminderList
                )
            }
        }
    }

    private fun onExpandCollapse(reminderInfo: ReminderInfoUi) {
        recentlyEditedReminderId = if (reminderInfo.isExpanded) {
            INVALID_REMINDER_ID
        } else {
            reminderInfo.reminderId
        }

        val activeReminderIndex = _uiState.value.reminders.indexOf(reminderInfo)
        val newReminderList = _uiState.value.reminders.toMutableList()

        val idOfRemindersWithExpandedState = //newReminderList.filter { it.isExpanded }.map { it.reminderId }
                newReminderList.indexesOf { it.isExpanded && it.reminderId != reminderInfo.reminderId }

        idOfRemindersWithExpandedState.forEach {
            newReminderList[it] = newReminderList[it].copy(isExpanded = false)
        }

        newReminderList[activeReminderIndex] = newReminderList[activeReminderIndex]
            .copy(isExpanded = !reminderInfo.isExpanded)

        _uiState.value = _uiState.value.copy(reminders = newReminderList)
    }

    private fun onEnabledDisabled(reminderInfo: ReminderInfoUi) {
        val newReminderList = _uiState.value.reminders.toMutableList()
        val indexOfUpdatedReminder = newReminderList.indexOf(reminderInfo)

        viewModelScope.launch {
            reminderRepository.updateReminder(
                time = LocalTime.parse(reminderInfo.formattedTime, DateTimeFormatter.ofPattern("HH:mm")),
                repeatDays = reminderInfo.repeatDays,
                isEnabled = !reminderInfo.isEnabled,
                id = reminderInfo.reminderId
            )

            // TODO: FIX IT
            val updatedReminder = mapToReminderInfoUi(
                listOf(reminderRepository.getReminder(reminderInfo.reminderId)!!),
                recentlyEditedReminderId
            ).first()

            newReminderList[indexOfUpdatedReminder] = updatedReminder
            _uiState.value = _uiState.value.copy(reminders = newReminderList)
        }
    }

    private fun mapToReminderInfoUi(data: List<ReminderEntity>, recentlyCreatedReminderId: Long): List<ReminderInfoUi> {
        return data.map {
            ReminderInfoUi(
                reminderId = it.id,
                formattedTime = it.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                isEnabled = it.isEnabled,
                repeatDays = it.repeatDays,
                isExpanded = recentlyCreatedReminderId == it.id
            )
        }
    }
}
